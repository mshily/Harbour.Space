package com.vina_esima.final_project.mainactivity.entries

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vina_esima.final_project.MainViewModel
import com.vina_esima.final_project.R
import com.vina_esima.final_project.analytics.EnrtyDB.EntryModel
import com.vina_esima.final_project.analytics.date
import com.vina_esima.final_project.converters._toDate
import com.vina_esima.final_project.converters._toDateDay
import com.vina_esima.final_project.ui.theme.FINAL_PROJECTTheme
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle
import kotlin.Boolean


@OptIn(ExperimentalMaterial3Api::class)
class AddExistingEntryActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val onBack: () -> Unit = { finish() }

        setContent {
            FINAL_PROJECTTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("add entry") },
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
                    AddEntryScreen(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        viewModel = viewModel,
                        onBack = onBack
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun AddEntryScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    var startText by remember { mutableStateOf("") }
    var endText by remember { mutableStateOf("") }
    var activityName by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    @RequiresApi(Build.VERSION_CODES.O)
    fun save() {
        try {
            val fmt  = DateTimeFormatter.ofPattern("yyyy:MM:dd:HH:mm:ss")
            val zone = ZoneId.systemDefault()

            val startInstant = LocalDateTime
                .parse(startText.trim(), fmt)
                .atZone(zone)
                .toInstant()

            val endInstant = LocalDateTime
                .parse(endText.trim(), fmt)
                .atZone(zone)
                .toInstant()

            require(!endInstant.isBefore(startInstant)) {
                "End time must be after start time"
            }

            var currentStart = startInstant
            while (currentStart.isBefore(endInstant)) {
                val nextMidnight = currentStart
                    .atZone(zone)
                    .toLocalDate()
                    .plusDays(1)
                    .atStartOfDay(zone)
                    .toInstant()

                val currentEnd =
                    if (endInstant.isBefore(nextMidnight)) endInstant else nextMidnight

                val startStr = fmt.format(currentStart.atZone(zone))
                val endStr   = fmt.format(currentEnd.atZone(zone))
                val dayStr   = startStr.substring(0, 10)

                val entry = EntryModel(
                    id = "$startStr-$endStr-$activityName",
                    start = _toDate(startStr),
                    end = _toDate(endStr),
                    day = _toDateDay(dayStr),
                    activity = activityName.trim()
                )
                viewModel.addEntry(entry)

                currentStart = currentEnd
            }

            dialogMessage = "Entry added successfully!"
            showDialog = true
        } catch (e: Exception) {
            Log.e("AddEntry", "Failed to add entry", e)
            dialogMessage = "Invalid format. Please use yyyy:MM:dd:HH:mm:ss."
            showDialog = true
        }
    }


    Box(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Add existing entry",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                value = startText,
                onValueChange = { startText = it },
                label = { Text("Start (yyyy:MM:dd:HH:mm:ss)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = endText,
                onValueChange = { endText = it },
                label = { Text("End (yyyy:MM:dd:HH:mm:ss)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = activityName,
                onValueChange = { activityName = it },
                label = { Text("Activity name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            fun isCorrect(str: String): Boolean {
                return try {
                    val fmt = DateTimeFormatter
                        .ofPattern("yyyy:MM:dd:HH:mm:ss")
//                        .withResolverStyle(ResolverStyle.STRICT)

                    LocalDateTime.parse(str, fmt)
                    true
                } catch (e: Exception) {
                    false
                }
            }


            Button(
                onClick = { save() },
                enabled = isCorrect(startText) && isCorrect(endText) && activityName.isNotBlank()
                        && _formatDurationInt(_toDate(startText), _toDate(endText)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Save")
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Add entry") },
                text = { Text(dialogMessage) },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog = false
                        if (dialogMessage.contains("success", ignoreCase = true)) onBack()
                    }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}
fun _formatDurationInt(start: date, end: date): Boolean {
    if (start.year > 3000) return false
    if (end.year > 3000) return false

    if (start.year < 0) return false
    if (end.year < 0) return false

    var startDay = start.year * 365 + start.month * 31 + start.day
    var endDay = end.year * 365 + end.month * 31 + end.day

    val diff = endDay - startDay
    Log.d("diff", diff.toString())
    return diff >= 0 && diff <= 7
}

