package com.vina_esima.final_project.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import com.vina_esima.final_project.MainViewModel
import com.vina_esima.final_project.analytics.EnrtyDB.EntryModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewListScreen(
    entries: List<EntryModel>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: MainViewModel = viewModel()
    val scope = rememberCoroutineScope()

    var entryToDelete by remember { mutableStateOf<EntryModel?>(null) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Daily List") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(entries, key = { _, it -> it.id }) { _, entry ->
                EntryCard(
                    entry = entry,
                    onDelete = { entryToDelete = entry }
                )
            }
        }

        if (entryToDelete != null) {
            AlertDialog(
                onDismissRequest = { entryToDelete = null },
                title = { Text("Delete entry?") },
                text = { Text("Are you sure you want to delete this entry? This action cannot be undone.") },
                confirmButton = {
                    TextButton(onClick = {
                        entryToDelete?.let { target ->
                            scope.launch {
                                viewModel.deleteById(target)
                            }
                        }
                        entryToDelete = null
                    }) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { entryToDelete = null }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
private fun EntryCard(
    entry: EntryModel,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = entry.activity,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Start: ${_formatTime(entry.start)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "End: ${_formatTime(entry.end)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Duration: ${_formatDuration(entry.start, entry.end)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(20.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}


fun _formatTime(value: date): String {
    val h = value.hour.toString().padStart(2, '0')
    val m = value.minute.toString().padStart(2, '0')
    val s = value.second.toString().padStart(2, '0')
    return "$h:$m:$s"
}

fun _formatDuration(start: date, end: date): String {
    val startSec = start.hour * 3600 + start.minute * 60 + start.second
    val endSec = end.hour * 3600 + end.minute * 60 + end.second
    val diff = (endSec - startSec).coerceAtLeast(0)
    val h = diff / 3600
    val m = (diff % 3600) / 60
    val s = diff % 60
    if (h == 0 && m == 0 && s == 0) return "24:00:00"
    else return "%02d:%02d:%02d".format(h, m, s)
}