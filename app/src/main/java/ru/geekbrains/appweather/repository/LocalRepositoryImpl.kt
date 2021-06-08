package ru.geekbrains.appweather.repository

import ru.geekbrains.appweather.model.Weather
import ru.geekbrains.appweather.room.FavoritesEntity
import ru.geekbrains.appweather.room.HistoryDAO
import ru.geekbrains.appweather.utils.convertCityToEntity
import ru.geekbrains.appweather.utils.convertFavoritesEntityToString
import ru.geekbrains.appweather.utils.convertHistoryEntityToWeather
import ru.geekbrains.appweather.utils.convertWeatherToEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDAO) :
    LocalRepository {
    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun getAllFavorites(): List<String> {
        return convertFavoritesEntityToString(localDataSource.getAllFavorites())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToEntity(weather))
    }

    override fun insertFavorites(city: String) {
        val favList: List<FavoritesEntity> = localDataSource.getAllFavorites()
        var exist = false
        for (item in favList) {
            if (item.city == city) exist = true
        }
        if (!exist) localDataSource.insertFavorites(convertCityToEntity(city))
    }
}