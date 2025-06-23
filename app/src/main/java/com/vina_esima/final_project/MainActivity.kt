package com.vina_esima.final_project

import android.R.attr.icon
import android.R.attr.label
import android.R.attr.onClick
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vina_esima.final_project.navigation.BottomNavigationScreen
import com.vina_esima.final_project.ui.theme.FINAL_PROJECTTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FINAL_PROJECTTheme {
                val viewModel: MainViewModel = viewModel()
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
                                    icon     = {
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
                        composable(BottomNavigationScreen.Analytics.route) { AnalyticsScreen() }
                        composable(BottomNavigationScreen.Home.route)     { HomeScreen() }
                        composable(BottomNavigationScreen.About.route)    { AboutScreen() }
                    }
                }

            }
        }
    }
}

@Composable
fun HomeScreen() {

}

@Composable
fun AnalyticsScreen() {

}

@Composable
fun AboutScreen() {

}
