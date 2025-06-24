package com.vina_esima.final_project.analytics.EnrtyDB.EntryRepository

import androidx.lifecycle.LiveData
import com.vina_esima.final_project.ActivityButtonDB.model.repository.AppDatabase
import com.vina_esima.final_project.analytics.EnrtyDB.EntryModel

class EntryRepository(private val EntryDao: EntryModelDAO) {
    val allEntries:  LiveData<List<EntryModel>> = EntryDao.getAll()

    fun insert(entry: EntryModel) {
        AppDatabase.databaseWriteExecutor.execute {
            EntryDao.insert(entry)
        }
    }

    fun deleteAll() {
        AppDatabase.databaseWriteExecutor.execute {
            EntryDao.deleteAll()
        }
    }

    fun deleteByID(id: String) {
        AppDatabase.databaseWriteExecutor.execute {
            EntryDao.deleteById(id)
        }
    }
}