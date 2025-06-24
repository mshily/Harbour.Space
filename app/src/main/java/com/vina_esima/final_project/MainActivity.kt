package com.vina_esima.final_project

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vina_esima.final_project.navigation.BottomNavigationScreen
import com.vina_esima.final_project.ui.theme.FINAL_PROJECTTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.vina_esima.final_project.mainactivity.AddNewActivityActivity
import com.vina_esima.final_project.mainactivity.DeleteActivityActivity
import com.vina_esima.final_project.mainactivity.DeleteActivityActivityScreen
import com.vina_esima.final_project.mainactivity.StartActivityActivity

class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FINAL_PROJECTTheme {

                val onClickToStartActivityActivity: () -> Unit = {
                    intent = Intent(this@MainActivity, StartActivityActivity::class.java)
                    startActivity(intent)
                }
                val onClickToAddNewActivityActivity: () -> Unit = {
                    intent = Intent(this@MainActivity, AddNewActivityActivity::class.java)
                    startActivity(intent)
                }

                val onClickToDeleteActivityActivity: () -> Unit = {
                    intent = Intent(this@MainActivity, DeleteActivityActivity::class.java)
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
                        composable(BottomNavigationScreen.Analytics.route) { AnalyticsScreen() }
                        composable(BottomNavigationScreen.Home.route)     {
                            MainScreen(onClickToStartActivityActivity = onClickToStartActivityActivity,
                                onClickToAddNewActivityActivity = onClickToAddNewActivityActivity,
                                onClickToDeleteActivityActivity = onClickToDeleteActivityActivity)
                        }
                        composable(BottomNavigationScreen.About.route)    { AboutScreen() }
                    }
                }

            }
        }
    }
}

@Composable
fun MainScreenColumn(
    modifier: Modifier = Modifier,
    onClickToStartActivityActivity: () -> Unit,
    onClickToAddNewActivityActivity: () -> Unit,
    onClickToDeleteActivityActivity: () ->  Unit
) {
    LazyColumn (
        modifier = modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        val list = listOf(
            Pair("start activity", onClickToStartActivityActivity),
            Pair("add a new activity", onClickToAddNewActivityActivity),
            Pair("delete an activity", onClickToDeleteActivityActivity)
        )

        items(list.size) {
            Card(
                modifier = Modifier.padding(8.dp)
                    .fillMaxSize()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                onClick = {
                    list[it].second()
                },
                shape = RoundedCornerShape(16.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = list[it].first,
                        textAlign = TextAlign.Center,
                        fontSize = 22.sp
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onClickToStartActivityActivity: () -> Unit,
    onClickToAddNewActivityActivity: () -> Unit,
    onClickToDeleteActivityActivity: () -> Unit
) {
    MainScreenColumn(modifier,
        onClickToStartActivityActivity,
        onClickToAddNewActivityActivity,
        onClickToDeleteActivityActivity)
}

@Composable
fun AnalyticsScreen(
    modifier: Modifier = Modifier
) {

}

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {

}
