package ru.geekbrains.appweather.repository

import ru.geekbrains.appweather.model.Weather
import ru.geekbrains.appweather.room.HistoryDAO
import ru.geekbrains.appweather.utils.convertHistoryEntityToWeather
import ru.geekbrains.appweather.utils.convertWeatherToEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDAO) :
    LocalRepository {
    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }
    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToEntity(weather))
    }
}