package ru.geekbrains.appweather.repository

import ru.geekbrains.appweather.model.Weather

interface LocalRepository {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)
    fun insertFavorites(city: String)
}