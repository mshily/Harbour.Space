package com.vina_esima.classexercises

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.vina_esima.classexercises.GalleryActivity
import com.vina_esima.classexercises.data.UnsplashItem
import com.vina_esima.classexercises.ui.theme.ClassExercisesTheme
import java.nio.file.WatchEvent

class PhotoDetailsActivity : ComponentActivity() {
    val onClickToPhotoViewActivity: (UnsplashItem) -> Unit = { image ->
        intent = Intent(this@PhotoDetailsActivity, PhotoViewActivity::class.java)
        intent.putExtra(EXTRA_UNSPLASH_IMAGE, image)
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
                    val imageRes = intent.extras!!.get(EXTRA_UNSPLASH_IMAGE) as UnsplashItem
                    PhotoDetailsActivity(
                        onClickToPhotoViewActivity,
                        modifier = Modifier.padding(innerPadding),
                        image = imageRes,
                    )
                }
            }
        }
    }
}


@Composable
fun PhotoDetailsActivity (
    onClickToPhotoViewActivity: (UnsplashItem) -> Unit,
    modifier: Modifier,
    image: UnsplashItem,
) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(300.dp)
        ) {

            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image.urls?.regular)
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.clickable (
                    onClick = {
                        onClickToPhotoViewActivity (image)}
                ),
                contentScale = ContentScale.Crop
            )


            Row(
                modifier = Modifier.padding(start = 32.dp, bottom = 8.dp)
                    .align(Alignment.BottomStart),
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            )

            {
                Text(
                    text = image.description.toString(),
                    color = Color.White,
                )
            }
        }


        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Row(modifier = Modifier.weight(5f)) {

                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image.user?.profile_image?.medium)
                        .build()
                )
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        TODO()
                    }.size(32.dp).clip(CircleShape).border(2.dp, Color.White, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = image.user?.name.toString(),
                    color = Color.White
                )
            }

            Image(
                painter = painterResource(id = R.drawable.ic_download),
                contentDescription = null,
                modifier = Modifier.clickable {
                    TODO()
                }.size(32.dp).weight(1f)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_favorite_border),
                contentDescription = null,
                modifier = Modifier.clickable {
                    TODO()
                }.size(32.dp).weight(1f)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_favorites),
                contentDescription = null,
                modifier = Modifier.clickable {
                    TODO()
                }.size(32.dp).weight(1f)
            )
        }



        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(16.dp)
        )

        val Description = listOf(
            Pair("Created at", image.created_at.toString()),
            Pair("Color", image.color.toString()),
            Pair("Height", image.height.toString()),
            Pair("Width", image.width.toString()),
            Pair("ID", image.id.toString()),
            Pair("Updated at", image.updated_at.toString()),
            Pair("Views", image.views.toString()),
            Pair("Downloads", image.downloads.toString()),
            Pair("Likes", image.likes.toString())
        )

        var idx = 0
        for (row in 0 until 3) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                for (column in 0 until 2) {
                    Column(
                        modifier = Modifier.padding(4.dp).weight(1f),
                    ) {
                        Text(text = Description[idx].first, color = Color.White)
                        Text(
                            text = Description[idx].second, color = Color.Gray
                        )
                    }
                    idx += 1
                }
            }
        }

        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(16.dp)
        )

        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            for (element in 0 until 3) {
                Column(
                    modifier = Modifier.padding(4.dp).weight(1f),
                ) {
                    Text(text = Description[idx].first, color = Color.White)
                    Text(
                        text = Description[idx].second, color = Color.Gray
                    )
                }
                idx += 1
            }
        }

        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(16.dp)
        )

        // Tags

        val listOfTags = listOf(
            image.user?.username.toString()
        )
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            for (tag in listOfTags) {
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .clickable {TODO()}
                ) {
                    Text(text = " $tag ", color = Color.Gray,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}