package ru.geekbrains.appweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.appweather.App.Companion.getHistoryDAO
import ru.geekbrains.appweather.repository.LocalRepository
import ru.geekbrains.appweather.repository.LocalRepositoryImpl

class FavoritesViewModel(
    val favoritesLiveData: MutableLiveData<List<String>> = MutableLiveData(),
    private val favoritesRepository: LocalRepository =
        LocalRepositoryImpl(getHistoryDAO())
) : ViewModel() {
    fun getAllFavorites() {
        favoritesLiveData.value = favoritesRepository.getAllFavorites()
    }
}