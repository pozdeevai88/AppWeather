package ru.geekbrains.appweather.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val city: String
)