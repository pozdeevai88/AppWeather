package ru.geekbrains.appweather

import android.app.Application
import androidx.room.Room
import ru.geekbrains.appweather.room.HistoryDAO
import ru.geekbrains.appweather.room.HistoryDataBase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var historyDB: HistoryDataBase? = null
        private const val DB_NAME = "History.db"
        fun getHistoryDAO(): HistoryDAO {
            if (historyDB == null) {
                synchronized(HistoryDataBase::class.java) {
                    if (historyDB == null) {
                        if (appInstance == null) throw
                        IllegalStateException("Application is null while creating DataBase")
                        historyDB = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            HistoryDataBase::class.java,
                            DB_NAME
                        )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return historyDB!!.historyDAO()
        }
    }
}