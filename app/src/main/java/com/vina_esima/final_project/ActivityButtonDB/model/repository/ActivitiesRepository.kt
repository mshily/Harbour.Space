package com.vina_esima.final_project.ActivityButtonDB.model.repository

import androidx.lifecycle.LiveData
import com.vina_esima.final_project.ActivityButtonDB.model.ActivityButtonsModel

class ActivitiesRepository(private val activitiesDao: ActivityButtonsModelDao) {
    val allActivities:  LiveData<List<ActivityButtonsModel>> = activitiesDao.getAll()
    fun insert(activity: ActivityButtonsModel) {
        AppDatabase.databaseWriteExecutor.execute {
            activitiesDao.insert(activity)
        }
    }

    fun insert(name: String) {
        AppDatabase.databaseWriteExecutor.execute {
            val nextId = (activitiesDao.getMaxId() ?: 0) + 1
            activitiesDao.insert(ActivityButtonsModel(id = nextId, name = name))
        }
    }


    fun deleteAll() {
        AppDatabase.databaseWriteExecutor.execute {
            activitiesDao.deleteAll()
        }
    }

    fun deleteByName(name: String) {
        AppDatabase.databaseWriteExecutor.execute {
            activitiesDao.deleteByName(name)
        }
    }
}