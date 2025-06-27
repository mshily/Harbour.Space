package com.vina_esima.final_project.mainactivity.starting

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vina_esima.final_project.MainViewModel
import com.vina_esima.final_project.R
import com.vina_esima.final_project.ui.theme.FINAL_PROJECTTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class StartActivityActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val onBack: () -> Unit = { finish() }

        val goToThisActivity: (String) -> Unit = { name ->
            Intent(this, ViewStartActivity::class.java).apply {
                putExtra("activity", name)
            }.also(::startActivity)
        }

        setContent {
            FINAL_PROJECTTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("start activity") },
                            navigationIcon = {
                                IconButton(onClick = onBack) {
                                    Icon(
                                        painterResource(id = R.drawable.ic_back_black),
                                        contentDescription = "Back",
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                        )
                    }
                ) { padding ->
                    StartActivityScreen(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        viewModel = viewModel,
                        goToThisActivity = goToThisActivity
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartActivityScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
    goToThisActivity: (String) -> Unit,
) {
    val activities by viewModel.getActivitiesFromDatabase().observeAsState(emptyList())
    val scope = rememberCoroutineScope()
    var activityToDelete by remember { mutableStateOf<String?>(null) }

    Box(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(activities, key = { it.id }) { activity ->
                ActivityItem(
                    name = activity.name,
                    onStart = { goToThisActivity(activity.name) },
                    onDelete = { activityToDelete = activity.name }
                )
            }
        }

        if (activityToDelete != null) {
            AlertDialog(
                onDismissRequest = { activityToDelete = null },
                title = { Text("Delete activity?") },
                text = { Text("Are you sure you want to delete \"$activityToDelete\"? This action cannot be undone.") },
                confirmButton = {
                    TextButton(onClick = {
                        activityToDelete?.let { target ->
                            scope.launch { viewModel.deleteActivityByName(target) }
                        }
                        activityToDelete = null
                    }) { Text("Delete") }
                },
                dismissButton = {
                    TextButton(onClick = { activityToDelete = null }) { Text("Cancel") }
                }
            )
        }
    }
}

@Composable
private fun ActivityItem(
    name: String,
    onStart: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onStart,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
        ) {
            Text(name, textAlign = TextAlign.Center)
        }

        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .clickable(onClick = onDelete),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Delete $name",
                modifier = Modifier.size(14.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}