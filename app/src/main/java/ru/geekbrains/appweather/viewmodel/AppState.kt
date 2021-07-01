package ru.geekbrains.appweather.viewmodel

import ru.geekbrains.appweather.model.Weather

sealed class AppState {
    data class Success(val weatherData: List<Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
