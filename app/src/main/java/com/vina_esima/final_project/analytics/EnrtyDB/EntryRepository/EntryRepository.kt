package com.vina_esima.final_project.analytics.EnrtyDB.EntryRepository

import androidx.lifecycle.LiveData
import com.vina_esima.final_project.analytics.EnrtyDB.EntryModel

class EntryRepository(private val EntryDao: EntryModelDAO) {
    val allEntries:  LiveData<List<EntryModel>> = EntryDao.getAll()

    fun insert(entry: EntryModel) {
        EntryDatabase.databaseWriteExecutor.execute {
            EntryDao.insert(entry)
        }
    }

    fun deleteAll() {
        EntryDatabase.databaseWriteExecutor.execute {
            EntryDao.deleteAll()
        }
    }

    fun deleteByID(id: String) {
        EntryDatabase.databaseWriteExecutor.execute {
            EntryDao.deleteById(id)
        }
    }

    fun getCountEntriesInDay (name: String): Int {
        return EntryDao.getCountEntriesInDay(name)
    }

    fun getEntriesForDay(dayString: String): LiveData<List<EntryModel>> {
        return EntryDao.getEntriesForDay(dayString)
    }
}