package com.vina_esima.final_project.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import com.vina_esima.final_project.R
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

sealed class BottomNavigationView (
    val route: String,
    @StringRes val stringResId: Int,
    @DrawableRes val drawResId: Int
) {
    data object DailyList : BottomNavigationView("DailyList",
        R.string.navigation_list, R.drawable.ic_list)

    data object Analytic : BottomNavigationView("Analytic",
        R.string.navigation_analytic, R.drawable.ic_analytic)
}