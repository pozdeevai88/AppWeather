package ru.geekbrains.appweather

data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0
)
fun getDefaultCity() = City("Москва", 55.558741, 37.378847)