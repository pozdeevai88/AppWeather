package ru.geekbrains.appweather

class RepositoryImpl : Repository {
    override fun getWeatherFromServer(): Weather {
        return Weather()
    }
    override fun getWeatherFromLocalStorage(): Weather {
        return Weather()
    }
}