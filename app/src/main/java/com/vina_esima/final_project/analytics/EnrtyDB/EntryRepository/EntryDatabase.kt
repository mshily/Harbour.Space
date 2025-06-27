package com.vina_esima.final_project.analytics.EnrtyDB.EntryRepository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vina_esima.final_project.analytics.EnrtyDB.EntryModel
import com.vina_esima.final_project.analytics.EnrtyDB.dateConverter
import java.util.concurrent.Executors

@Database(
    entities = [
        EntryModel::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(dateConverter::class)
abstract class EntryDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryModelDAO

    companion object {
        @Volatile
        private var INSTANCE: EntryDatabase? = null
        val databaseWriteExecutor = Executors.newFixedThreadPool(2)

        fun getDatabase(context: Context): EntryDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(context,  EntryDatabase::class.java,  "entry_db" ).build()
                INSTANCE = db
                db
            }
        }
    }
}