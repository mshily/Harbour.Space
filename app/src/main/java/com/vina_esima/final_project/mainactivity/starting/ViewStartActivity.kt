package com.vina_esima.final_project.mainactivity.starting

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.vina_esima.final_project.MainViewModel
import com.vina_esima.final_project.R
import com.vina_esima.final_project.UnsplashViewModel
import com.vina_esima.final_project.analytics.EnrtyDB.EntryModel
import com.vina_esima.final_project.converters._toDate
import com.vina_esima.final_project.converters._toDateDay
import com.vina_esima.final_project.data.data.UnsplashItem
import com.vina_esima.final_project.ui.theme.FINAL_PROJECTTheme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
class ViewStartActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val unsplashViewModel: UnsplashViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val onBack: () -> Unit = { finish() }

        val activityName = intent.getStringExtra("activity") ?: "Unknown"
        Log.d("ViewStartActivity", "Opened for $activityName")

        setContent {
            FINAL_PROJECTTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(activityName) },
                            navigationIcon = {
                                IconButton(onClick = onBack) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_back_black),
                                        contentDescription = null,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                        )
                    }
                ) { padding ->
                    ViewStartActivityScreen(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        viewModel = viewModel,
                        activityName = activityName,
                        unsplashViewModel = unsplashViewModel,
                        onSearch = unsplashViewModel::search
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@Composable
fun ViewStartActivityScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    unsplashViewModel: UnsplashViewModel,
    activityName: String,
    onSearch: (String) -> Unit
) {
    var seconds by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    var startEpoch by remember { mutableStateOf<Long?>(null) }
    var timer by remember { mutableStateOf<Timer?>(null) }

    var showConfirmDialog by remember { mutableStateOf(false) }
    var showSaveDialog by remember { mutableStateOf(false) }

    val formattedTime by remember(seconds) {
        derivedStateOf {
            val h = seconds / 3600
            val m = (seconds % 3600) / 60
            val s = seconds % 60
            "%02d:%02d:%02d".format(h, m, s)
        }
    }

    DisposableEffect(isRunning) {
        if (isRunning) {
            if (startEpoch == null) startEpoch = System.currentTimeMillis()
            timer = Timer().apply {
                scheduleAtFixedRate(object : TimerTask() {
                    override fun run() {
                        seconds += 1
                    }
                }, 1000, 1000)
            }
        } else {
            timer?.cancel()
        }
        onDispose { timer?.cancel() }
    }
    val images = unsplashViewModel.images.observeAsState(emptyList())
    Log.d("ViewStartActivityScreen", "images: ${images.value}")
    onSearch(activityName)
    if (images.value.isEmpty()) return
    val image = images.value[0]

    Box(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(48.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
            ) {
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image.urls?.regular)
                        .build()
                )

                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
                        .padding(32.dp)
                ) {
                    Text(
                        text = formattedTime,
                        style = MaterialTheme.typography.displayLarge.copy(fontSize = 64.sp),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { isRunning = true },
                    enabled = !isRunning,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .height(56.dp)
                        .weight(1f)
                ) { Text("Start") }

                Button(
                    onClick = { if (isRunning) showConfirmDialog = true },
                    enabled = isRunning,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .height(56.dp)
                        .weight(1f)
                ) { Text("Stop") }
            }
        }

        if (showConfirmDialog) {
            AlertDialog(
                onDismissRequest = { showConfirmDialog = false },
                title = { Text("Finish session?") },
                text = { Text("Do you want to stop the timer and save the session?") },
                confirmButton = {
                    TextButton(onClick = {
                        showConfirmDialog = false
                        val startMs = startEpoch ?: System.currentTimeMillis()
                        val endMs = System.currentTimeMillis()
                        saveEntryToDatabase(startMs, endMs, activityName, viewModel)

                        isRunning = false
                        seconds = 0
                        startEpoch = null
                        showSaveDialog = true
                    }) { Text("Yes") }
                },
                dismissButton = {
                    TextButton(onClick = { showConfirmDialog = false }) { Text("No") }
                }
            )
        }

        if (showSaveDialog) {
            AlertDialog(
                onDismissRequest = { showSaveDialog = false },
                title = { Text("Entry saved") },
                text = { Text("Your session has been recorded. Good job!") },
                confirmButton = {
                    TextButton(onClick = { showSaveDialog = false }) { Text("OK") }
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun saveEntryToDatabase(
    startMs: Long,
    endMs: Long,
    activityName: String,
    viewModel: MainViewModel
) {
    val zone = ZoneId.systemDefault()
    val fmt = DateTimeFormatter.ofPattern("yyyy:MM:dd:HH:mm:ss")

    var currentStart = Instant.ofEpochMilli(startMs)
    val sessionEnd = Instant.ofEpochMilli(endMs)

    while (currentStart.isBefore(sessionEnd)) {
        val nextMidnight = currentStart
            .atZone(zone)
            .toLocalDate()
            .plusDays(1)
            .atStartOfDay(zone)
            .toInstant()

        val currentEnd = if (sessionEnd.isBefore(nextMidnight)) sessionEnd else nextMidnight

        val startStr = fmt.format(currentStart.atZone(zone))
        val endStr = fmt.format(currentEnd.atZone(zone))
        val dayStr = startStr.substring(0, 10)

        val entry = EntryModel(
            id = "${currentStart.toEpochMilli()}-${currentEnd.toEpochMilli()}-$activityName",
            start = _toDate(startStr),
            end = _toDate(endStr),
            day = _toDateDay(dayStr),
            activity = activityName
        )
        viewModel.addEntry(entry)

        currentStart = currentEnd
    }
}
