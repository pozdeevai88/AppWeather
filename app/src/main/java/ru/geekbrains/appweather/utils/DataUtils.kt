package ru.geekbrains.appweather.utils

import ru.geekbrains.appweather.model.FactDTO
import ru.geekbrains.appweather.model.Weather
import ru.geekbrains.appweather.model.WeatherDTO
import ru.geekbrains.appweather.model.getDefaultCity

fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
    val fact: FactDTO = weatherDTO.fact!!
    return listOf(
        Weather(
            getDefaultCity(), fact.temp!!, fact.feels_like!!,
        fact.condition!!)
    )
}