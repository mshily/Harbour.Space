package com.vina_esima.final_project.analytics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vina_esima.final_project.ui.theme.FINAL_PROJECTTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vina_esima.final_project.MainViewModel
import com.vina_esima.final_project.navigation.BottomNavigationView
import androidx.compose.material3.Icon

class ViewAnalyticsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val onBack:() -> Unit = { finish() }

        setContent {
            FINAL_PROJECTTheme {
                val viewModel: MainViewModel = viewModel()


                val selectedScreen by viewModel.selectedScreenView.observeAsState(
                    BottomNavigationView.DailyList
                )

                val navController = rememberNavController()

                val items = listOf(
                    BottomNavigationView.DailyList,
                    BottomNavigationView.Analytic,
                )

                val year = intent.getIntExtra("year", 0)
                val month = intent.getIntExtra("month", 0)
                val day = intent.getIntExtra("day", 0)

                Log.d("ViewAnalyticsActivity", "${day}")

                val entries by viewModel.getEntriesForDay(year, month, day).observeAsState(emptyList())
                Log.d("ViewAnalyticsActivity", "${entries.size}")

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            items.forEach { screen ->
                                NavigationBarItem(
                                    selected = selectedScreen == screen,
                                    onClick  = {
                                        viewModel.selectScreenView(screen)
                                        navController.navigate(screen.route)
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = screen.drawResId),
                                            contentDescription = null,
                                            modifier = Modifier.size(32.dp)
                                        )
                                    },
                                    label    = {
                                        Text(text = stringResource(id = screen.stringResId))
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = selectedScreen.route,
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        composable(BottomNavigationView.DailyList.route) { ViewListScreen(
                            modifier = Modifier,
                            entries = entries,
                            onBack = onBack
                        ) }
                        composable(BottomNavigationView.Analytic.route) { ViewAnalyticsScreen(
                            modifier = Modifier,
                            entries = entries,
                            onBack = onBack
                        ) }

                    }
                }
            }
        }
    }
}





