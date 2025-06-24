package com.vina_esima.final_project.analytics.EnrtyDB.EntryRepository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vina_esima.final_project.analytics.EnrtyDB.EntryModel
import java.util.concurrent.Executors

@Database(
    entities = [
        EntryModel::class
    ],
    version = 1,
    exportSchema = false
)
abstract class EntryDatabase : RoomDatabase() {
    abstract fun activitiesDao(): EntryModelDAO

    companion object {
        @Volatile
        private var INSTANCE: EntryDatabase? = null
        val databaseWriteExecutor = Executors.newFixedThreadPool(2)

        fun getDatabase(context: Context): EntryDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(context,  EntryDatabase::class.java,  "db" ).build()
                INSTANCE = db
                db
            }
        }
    }
}