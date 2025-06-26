package com.vina_esima.final_project

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vina_esima.final_project.ActivityButtonDB.model.ActivityButtonsModel
import com.vina_esima.final_project.navigation.BottomNavigationScreen
import com.vina_esima.final_project.ActivityButtonDB.model.repository.ActivitiesRepository
import com.vina_esima.final_project.ActivityButtonDB.model.repository.AppDatabase
import com.vina_esima.final_project.analytics.EnrtyDB.EntryModel
import com.vina_esima.final_project.analytics.EnrtyDB.EntryRepository.EntryDatabase
import com.vina_esima.final_project.analytics.EnrtyDB.EntryRepository.EntryRepository
import com.vina_esima.final_project.navigation.BottomNavigationView

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

    // db Entry

    val entryDatabase = EntryDatabase.getDatabase(application)
    private val entryRepository = EntryRepository(entryDatabase.entryDao())

    fun addEntry(entry: EntryModel) {
        entryRepository.insert(entry)
    }
    fun deleteById(entry: EntryModel) {
        entryRepository.deleteByID(entry.id)
    }


    fun getEntriesForDay(year: Int, month: Int, day: Int): LiveData<List<EntryModel>> {
        val dayString = "$year:$month:$day"
        return entryRepository.getEntriesForDay(dayString)
    }

    // bottom navigation main
    private val _selectedScreen = MutableLiveData<BottomNavigationScreen>(
        BottomNavigationScreen.Home
    )

    val selectedScreen: LiveData<BottomNavigationScreen> = _selectedScreen
    fun selectScreen(screen: BottomNavigationScreen) {
        _selectedScreen.value = screen
    }

    // bottom navigation view
    private val _selectedScreenView = MutableLiveData<BottomNavigationView>(
        BottomNavigationView.DailyList
    )

    val selectedScreenView: LiveData<BottomNavigationView> = _selectedScreenView
    fun selectScreenView(screen: BottomNavigationView) {
        _selectedScreenView.value = screen
    }

}
