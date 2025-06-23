package com.vina_esima.final_project

import android.R.attr.icon
import android.R.attr.label
import android.R.attr.onClick
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

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
                        composable(BottomNavigationScreen.Home.route)     { MainScreen() }
                        composable(BottomNavigationScreen.About.route)    { AboutScreen() }
                    }
                }

            }
        }
    }
}

@Composable
fun MainScreenColumn(
    modifier: Modifier = Modifier
) {
    LazyColumn (
        modifier = modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        val list = listOf(
            "start activity",
            "add exist activity"
        )

        items(list.size) {
            Card(
                modifier = Modifier.padding(8.dp)
                    .fillMaxSize()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                onClick = {
                    TODO()
                },
                shape = RoundedCornerShape(16.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = list[it],
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
    modifier: Modifier = Modifier
) {
    MainScreenColumn(modifier)
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
