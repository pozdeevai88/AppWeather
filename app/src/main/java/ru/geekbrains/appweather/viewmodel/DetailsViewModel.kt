package ru.geekbrains.appweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import ru.geekbrains.appweather.model.FactDTO
import ru.geekbrains.appweather.model.Weather
import ru.geekbrains.appweather.model.WeatherDTO
import ru.geekbrains.appweather.model.getDefaultCity
import ru.geekbrains.appweather.repository.DetailsRepository
import ru.geekbrains.appweather.repository.DetailsRepositoryImpl
import ru.geekbrains.appweather.repository.RemoteDataSource
import java.io.IOException

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailsViewModel(
    private val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepositoryImpl: DetailsRepository =
        DetailsRepositoryImpl(RemoteDataSource())
) : ViewModel() {
    fun getLiveData() = detailsLiveData
    fun getWeatherFromRemoteSource(requestLink: String) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getWeatherDetailsFromServer(requestLink, callBack)
    }
    private val callBack = object : Callback {
        @Throws(IOException::class)
        override fun onResponse(call: Call?, response: Response) {
            val serverResponse: String? = response.body()?.string()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }
        override fun onFailure(call: Call?, e: IOException?) {
            detailsLiveData.postValue(
                AppState.Error(Throwable(e?.message ?:
            REQUEST_ERROR
                )))
        }
        private fun checkResponse(serverResponse: String): AppState {
            val weatherDTO: WeatherDTO =
                Gson().fromJson(serverResponse, WeatherDTO::class.java)
            val fact = weatherDTO.fact
            return if (fact == null || fact.temp == null || fact.feels_like ==
                null || fact.condition.isNullOrEmpty()) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.Success(convertDtoToModel(weatherDTO))
            }
        }

        fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
            val fact: FactDTO = weatherDTO.fact!!
            return listOf(
                Weather(
                    getDefaultCity(), fact.temp!!, fact.feels_like!!,
                fact.condition!!)
            )
        }
    }
}