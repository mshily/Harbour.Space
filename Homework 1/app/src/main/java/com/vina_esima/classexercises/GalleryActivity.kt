package com.vina_esima.classexercises

import android.R.attr.content
import android.R.attr.onClick
import android.R.attr.shape
import android.R.string
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vina_esima.classexercises.ui.theme.ClassExercisesTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.internal.composableLambdaN
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource

class GalleryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBack:() -> Unit = { finish() }

        val onClickToPhotoDetailsActivity: (Pair<Int, String>) -> Unit = { image ->
            intent = Intent(this@GalleryActivity, PhotoDetailsActivity::class.java)
            intent.putExtra("image", image.first)
            intent.putExtra("desc", image.second)
            startActivity(intent)
        }

        setContent {
            ClassExercisesTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Black,
                ) { innerPadding ->
                    GalleryScreen(
                        modifier = Modifier.padding(innerPadding),
                        onClickToPhotoDetailsActivity = onClickToPhotoDetailsActivity,
                        onBack = onBack
                    )
                }
            }
        }
    }
}

@Composable
fun GalleryLazyColumn(
    modifier: Modifier,
    onClickToPhotoDetailsActivity: (Pair<Int, String>) -> Unit,
) {
    LazyColumn (
        modifier = modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val images = listOf(
            Pair(R.drawable.ic_brokoli_bobi, "Brokoli BOBI"),
            Pair(R.drawable.ic_marcel_mango, "Marcel Mango"),
            Pair(R.drawable.ic_pork, "JHON PORK"),
            Pair(R.drawable.ic_meat_man, "Meat Man"),
            Pair(R.drawable.ic_beer, "Beer"),
            Pair(R.drawable.ic_putin, "Putin")
        )

        items(images) { image ->
            Card(
                modifier = Modifier.padding(8.dp)
                    .fillMaxSize()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                onClick = {
                    onClickToPhotoDetailsActivity(image)
                },
                shape = RoundedCornerShape(16.dp),
            ) {
                Image (
                    painter = painterResource(id = image.first),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    modifier: Modifier,
    onClickToPhotoDetailsActivity: (Pair < Int, String >) -> Unit,
    onBack: () -> Unit
) {
    Scaffold (
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = { Text(text = "Gallery", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                ),
                actions = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onBack,
                shape = CircleShape,
                containerColor = Color.White
                ){
                    Icon(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
            }
        }
    )
    { innerPadding ->
        GalleryLazyColumn (
            modifier = Modifier.padding(innerPadding),
            onClickToPhotoDetailsActivity,
        )
    }
}