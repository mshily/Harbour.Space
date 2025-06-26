package com.vina_esima.final_project.analytics.CalendarDB.CalendarRepository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vina_esima.final_project.analytics.EnrtyDB.CalendarConverter
import com.vina_esima.final_project.analytics.EnrtyDB.CalendarModel
import com.vina_esima.final_project.analytics.EnrtyDB.EntryModel
import com.vina_esima.final_project.analytics.EnrtyDB.EntryRepository.EntryModelDAO
import com.vina_esima.final_project.analytics.EnrtyDB.dateConverter
import java.util.Calendar
import java.util.concurrent.Executors

@Database(
    entities = [
        CalendarModel::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(CalendarConverter::class)
abstract class CalendarDatabase : RoomDatabase() {
    abstract fun calendarDao(): CalendarModelDAO

    companion object {
        @Volatile
        private var INSTANCE: CalendarDatabase? = null
        val databaseWriteExecutor = Executors.newFixedThreadPool(2)

        fun getDatabase(context: Context): CalendarDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(context,  CalendarDatabase::class.java,  "db" ).build()
                INSTANCE = db
                db
            }
        }
    }
}