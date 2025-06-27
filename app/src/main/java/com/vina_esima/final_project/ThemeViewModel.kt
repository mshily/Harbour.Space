package com.vina_esima.final_project

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeViewModel : ViewModel() {
    enum class ThemeMode {
        SYSTEM,
        LIGHT,
        DARK
    }

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    @Composable
    fun isDarkTheme(): Boolean {
        val currentThemeMode by themeMode.collectAsState()
        return when (currentThemeMode) {
            ThemeMode.SYSTEM -> isSystemInDarkTheme()
            ThemeMode.LIGHT -> false
            ThemeMode.DARK -> true
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }

    fun toggleTheme() {
        _themeMode.value = when (_themeMode.value) {
            ThemeMode.SYSTEM -> ThemeMode.LIGHT
            ThemeMode.LIGHT -> ThemeMode.DARK
            ThemeMode.DARK -> ThemeMode.SYSTEM
        }
    }

    @Composable
    fun getCurrentThemeMode(): ThemeMode {
        return themeMode.collectAsState().value
    }

    companion object {
        val instance = ThemeViewModel()
    }
}
