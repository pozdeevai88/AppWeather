package ru.geekbrains.appweather.repository

import ru.geekbrains.appweather.model.Weather
import ru.geekbrains.appweather.model.getRussianCities
import ru.geekbrains.appweather.model.getWorldCities

interface Repository {
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
}

class RepositoryImpl : Repository {
    override fun getWeatherFromServer() = Weather()
    override fun getWeatherFromLocalStorageRus() = getRussianCities()
    override fun getWeatherFromLocalStorageWorld() = getWorldCities()
}