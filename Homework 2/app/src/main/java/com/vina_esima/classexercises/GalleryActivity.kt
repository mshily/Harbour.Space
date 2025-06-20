package com.vina_esima.classexercises

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.vina_esima.classexercises.data.SearchItem
import com.vina_esima.classexercises.data.UnsplashApiProvider
import com.vina_esima.classexercises.data.UnsplashItem
import com.vina_esima.classexercises.data.cb.UnsplashResult
import com.vina_esima.classexercises.ui.theme.ClassExercisesTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController

enum class TopTab(@StringRes val resId: Int) {
    IMAGES(resId = R.string.main_tab_images),
    COLLECTIONS(resId = R.string.main_tab_collections)
}

class GalleryActivity : ComponentActivity() {
    val viewModel: GalleryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBack:() -> Unit = { finish() }

        val onClickToPhotoDetailsActivity: (UnsplashItem) -> Unit = { image ->
            intent = Intent(this@GalleryActivity, PhotoDetailsActivity::class.java)
            intent.putExtra(EXTRA_UNSPLASH_IMAGE, image)
            startActivity(intent)
        }

        setContent {

            ClassExercisesTheme {
                val navController = rememberNavController()
                val actions = listOf(TopTab.IMAGES, TopTab.COLLECTIONS)

                val selected = rememberSaveable {
                    mutableIntStateOf(TopTab.IMAGES.ordinal)
                }

                val images = viewModel.images.observeAsState(emptyList())
                val isLoading = viewModel.isLoading.observeAsState(false)
                val lastQuery = viewModel.lastQuery.observeAsState("")

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Black,
                ) { innerPadding ->

                    GalleryScreen(
                        modifier = Modifier.padding(innerPadding),
                        onClickToPhotoDetailsActivity = onClickToPhotoDetailsActivity,
                        onBack = onBack,
                        onSearch = viewModel::search,
                        onRefresh = viewModel::refresh,
                        images = images.value,
                        isLoading = isLoading.value == true,
                        lastQuery = lastQuery.value,
                        actions = actions,
                        selected = selected,
                        onTableSelected = {
                            selected.intValue = it
                        }
                    )
                }
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun GalleryScreen(
    modifier: Modifier,
    onClickToPhotoDetailsActivity: (UnsplashItem) -> Unit,
    onBack: () -> Unit,
    onSearch: (String) -> Unit,
    onRefresh: () -> Unit,
    images: List<UnsplashItem>,
    isLoading: Boolean,
    lastQuery: String,
    actions: List<TopTab>,
    selected: MutableState < Int >,
    onTableSelected: (Int) -> Unit,
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
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    )
    { innerPadding ->

        Column (
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            TabRow(selectedTabIndex = selected.value) {
                actions.forEachIndexed { index, action ->
                    Tab(
                        modifier = Modifier.height(40.dp),
                        selected = selected.value == index,
                        onClick = {
                            selected.value = index
                            onTableSelected(index)
                        },
                    ) {
                        Text(text = stringResource(id = action.resId))
                    }
                }
            }

            Spacer(modifier = Modifier.padding(16.dp))

            val search = lastQuery

            OutlinedTextField(
                value = search,
                onValueChange = {
                        value: String -> onSearch(value)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),

                keyboardActions = KeyboardActions {
                    onSearch(search)
                },
                modifier = Modifier.height(64.dp).fillMaxWidth(),
                label = {Text("Search")},
                textStyle = LocalTextStyle.current.copy(color = Color.White),
            )

            when(selected.value) {
                TopTab.IMAGES.ordinal -> {
                    val pullRefreshState = rememberPullToRefreshState()
                    PullToRefreshBox(
                        state = pullRefreshState,
                        isRefreshing = isLoading,
                        onRefresh = onRefresh
                    ) {
                        GalleryLazyColumn (
                            modifier = Modifier.padding(16.dp),
                            onClickToPhotoDetailsActivity,
                            onSearch,
                            onRefresh,
                            images,
                            lastQuery
                        )
                    }
                }
                TopTab.COLLECTIONS.ordinal -> {
                    Text(text = "kek")
                }

            }


        }


    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun GalleryLazyColumn(
    modifier: Modifier,
    onClickToPhotoDetailsActivity: (UnsplashItem) -> Unit,
    onSearch: (String) -> Unit,
    onRefresh: () -> Unit,
    images: List<UnsplashItem>,
    lastQuery: String
) {

    LazyColumn (
        modifier = modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(images) { image ->
            Card(
                modifier = Modifier.padding(8.dp)
                    .fillMaxSize()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                onClick = {
                    onClickToPhotoDetailsActivity(TODO())
                },
                shape = RoundedCornerShape(16.dp),
            ) {
//                Text(text = "aha")
                FetchedItem(image, onClickToPhotoDetailsActivity)
            }
        }
    }
}

@Composable
fun FetchedItem(
    image: UnsplashItem,
    onClickToPhotoDetailsActivity: (UnsplashItem) -> Unit,
    ) {


    Card(shape = RoundedCornerShape(16.dp)
        , modifier = Modifier.fillMaxWidth().height(200.dp)
    )
    {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image.urls?.regular)
                .build()
        )

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.clickable (onClick = {onClickToPhotoDetailsActivity (image)}),
            contentScale = ContentScale.Crop
        )

        Column (verticalArrangement = Arrangement.Bottom, modifier = Modifier.padding(16.dp).fillMaxHeight()) {
            Text(text = image.description ?: "No description", fontWeight = FontWeight.Bold)
            Text(text = image.user?.name ?: "No name")
        }
    }
}
