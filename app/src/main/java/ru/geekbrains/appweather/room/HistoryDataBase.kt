package ru.geekbrains.appweather.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HistoryEntity::class, FavoritesEntity::class],
    version = 3,
    exportSchema = false
)

abstract class HistoryDataBase : RoomDatabase() {
    abstract fun historyDAO(): HistoryDAO
}
