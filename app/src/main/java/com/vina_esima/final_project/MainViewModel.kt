package com.vina_esima.final_project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vina_esima.final_project.navigation.BottomNavigationScreen

class MainViewModel : ViewModel() {
    private val _selectedScreen = MutableLiveData<BottomNavigationScreen>(
        BottomNavigationScreen.Home
    )

    val selectedScreen: LiveData<BottomNavigationScreen> = _selectedScreen
    fun selectScreen(screen: BottomNavigationScreen) {
        _selectedScreen.value = screen
    }

    fun selectedScreen (screen: BottomNavigationScreen) {
        _selectedScreen.value = screen
    }
}
