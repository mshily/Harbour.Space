package com.vina_esima.final_project.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import com.vina_esima.final_project.R
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

sealed class BottomNavigationScreen (
    val route: String,
    @StringRes val stringResId: Int,
    @DrawableRes val drawResId: Int
) {

    data object Home : BottomNavigationScreen("Main",
        R.string.navigation_home, R.drawable.ic_clock)

    data object About : BottomNavigationScreen("About",
        R.string.navigation_about, R.drawable.ic_info)

    data object Analytics : BottomNavigationScreen("Analytics",
        R.string.navigation_analytics, R.drawable.ic_analytics)
}