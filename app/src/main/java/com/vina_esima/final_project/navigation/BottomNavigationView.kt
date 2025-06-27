package com.vina_esima.final_project.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.vina_esima.final_project.R

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