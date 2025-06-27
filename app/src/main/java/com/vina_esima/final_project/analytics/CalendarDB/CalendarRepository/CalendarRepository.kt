package com.vina_esima.final_project.analytics.CalendarDB.CalendarRepository

import androidx.lifecycle.LiveData
import com.vina_esima.final_project.ActivityButtonDB.model.repository.AppDatabase
import com.vina_esima.final_project.analytics.EnrtyDB.CalendarModel
import com.vina_esima.final_project.analytics.EnrtyDB.EntryModel

class CalendarRepository(private val CalendarDao: CalendarModelDAO) {
    val allEntries:  LiveData<List<CalendarModel>> = CalendarDao.getAll()


    fun deleteAll() {
        AppDatabase.databaseWriteExecutor.execute {
            CalendarDao.deleteAll()
        }
    }

    fun deleteByID(id: String) {
        AppDatabase.databaseWriteExecutor.execute {
            CalendarDao.deleteById(id)
        }
    }
}