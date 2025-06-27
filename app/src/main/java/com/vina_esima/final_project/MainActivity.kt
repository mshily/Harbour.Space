package com.vina_esima.final_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vina_esima.final_project.navigation.BottomNavigationScreen
import com.vina_esima.final_project.ui.theme.FINAL_PROJECTTheme
import androidx.compose.runtime.livedata.observeAsState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.vina_esima.final_project.analytics.ViewAnalyticsActivity
import com.vina_esima.final_project.mainactivity.AddNewActivityActivity
import com.vina_esima.final_project.mainactivity.entries.AddExistingEntryActivity
import com.vina_esima.final_project.mainactivity.starting.StartActivityActivity
import com.vina_esima.final_project.mainactivity.MainItems.AboutScreen
import com.vina_esima.final_project.mainactivity.MainItems.AnalyticsScreen
import com.vina_esima.final_project.mainactivity.MainItems.MainScreen

class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            FINAL_PROJECTTheme {
                Log.d("LoginActivity", "onCreate")

                val goToViewAnalyticsActivity: (year: Int, month: Int, day: Int) -> Unit = { year, month, day ->
                    intent = Intent(this@MainActivity, ViewAnalyticsActivity::class.java)
                    intent.putExtra("year", year)
                    intent.putExtra("month", month)
                    intent.putExtra("day", day)
                    startActivity(intent)
                }

                val onLogOut = {
                    Firebase.auth.signOut()
                    intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
                val onClickToStartActivityActivity: () -> Unit = {
                    intent = Intent(this@MainActivity, StartActivityActivity::class.java)
                    startActivity(intent)
                }
                val onClickToAddNewActivityActivity: () -> Unit = {
                    intent = Intent(this@MainActivity, AddNewActivityActivity::class.java)
                    startActivity(intent)
                }

                val onClickToDeleteActivityActivity: () -> Unit = {
                    intent = Intent(this@MainActivity, AddExistingEntryActivity::class.java)
                    startActivity(intent)
                }

                val selectedScreen by viewModel.selectedScreen.observeAsState(
                    BottomNavigationScreen.Home
                )

                val navController = rememberNavController()

                val items = listOf(
                    BottomNavigationScreen.Analytics,
                    BottomNavigationScreen.Home,
                    BottomNavigationScreen.About
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            items.forEach { screen ->
                                NavigationBarItem(
                                    selected = selectedScreen == screen,
                                    onClick  = {
                                        viewModel.selectScreen(screen)
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
                        composable(BottomNavigationScreen.Analytics.route) { AnalyticsScreen(
                            modifier = Modifier,
                            onGoToViewAnalyticsActivity = goToViewAnalyticsActivity
                        ) }
                        composable(BottomNavigationScreen.Home.route)     {
                            MainScreen(onClickToStartActivityActivity = onClickToStartActivityActivity,
                                onClickToAddNewActivityActivity = onClickToAddNewActivityActivity,
                                onClickToDeleteActivityActivity = onClickToDeleteActivityActivity)
                        }
                        composable(BottomNavigationScreen.About.route)  { AboutScreen(
                            onLogOut = onLogOut
                        ) }
                    }
                }

            }
        }
    }
}

