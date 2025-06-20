package com.vina_esima.classexercises

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import com.vina_esima.classexercises.data.UnsplashItem
import com.vina_esima.classexercises.data.cb.UnsplashResult
import com.vina_esima.classexercises.ui.theme.ClassExercisesTheme

class MainActivity : ComponentActivity() {
    private val onClickToGalleryActivity: () -> Unit = {
        val intent = Intent(this@MainActivity, GalleryActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ClassExercisesTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.Black),
                    containerColor = Color.Black,
                ) { innerPadding ->
                    MainActivityScreen(
                        modifier = Modifier.padding(innerPadding),
                        onClickToGalleryActivity
                    )
                }
            }
        }
    }
}

@Composable
fun MainActivityScreen(
    modifier: Modifier = Modifier,
    onClickToGalleryActivity: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment =  Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                onClickToGalleryActivity()
            },
        ) {
            Text(text = "Go to the Gallery!", color = Color.White)
        }
    }

}