package ru.geekbrains.appweather.ui.home

import android.os.SystemClock.sleep
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.appweather.AppState
import ru.geekbrains.appweather.Repository
import ru.geekbrains.appweather.RepositoryImpl

class HomeViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {
    fun getLiveData() = liveDataToObserve
    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)
    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)
    fun getWeatherFromRemoteSource() = getDataFromLocalSource(isRussian = true)
    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(
                AppState.Success(
                    if (isRussian)
                        repositoryImpl.getWeatherFromLocalStorageRus() else
                        repositoryImpl.getWeatherFromLocalStorageWorld()
                )
            )
        }.start()
    }
}