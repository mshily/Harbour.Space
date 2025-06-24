package com.vina_esima.final_project

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vina_esima.final_project.ActivityButtonDB.model.ActivityButtonsModel
import com.vina_esima.final_project.navigation.BottomNavigationScreen
import com.vina_esima.final_project.ActivityButtonDB.model.repository.ActivitiesRepository
import com.vina_esima.final_project.ActivityButtonDB.model.repository.AppDatabase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // data bases
    private val database = AppDatabase.getDatabase(application)
    private val repository = ActivitiesRepository(database.activitiesDao())


    fun getActivitiesFromDatabase(): LiveData<List<ActivityButtonsModel>> {
        return repository.allActivities
    }

    fun addActivity(activity: ActivityButtonsModel) {
        repository.insert(activity)
    }

    fun deleteActivityByName(name: String) {
        repository.deleteByName(name)
    }

    fun deleteAllActivities() {
        repository.deleteAll()
    }

    fun addActivity(name: String) {
        repository.insert(name)
    }

    private val _selectedScreen = MutableLiveData<BottomNavigationScreen>(
        BottomNavigationScreen.Home
    )

    val selectedScreen: LiveData<BottomNavigationScreen> = _selectedScreen
    fun selectScreen(screen: BottomNavigationScreen) {
        _selectedScreen.value = screen
    }

}
