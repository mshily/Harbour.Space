package com.vina_esima.final_project.mainactivity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vina_esima.final_project.MainViewModel
import com.vina_esima.final_project.R
import com.vina_esima.final_project.ui.theme.FINAL_PROJECTTheme

@OptIn(ExperimentalMaterial3Api::class)
class AddNewActivityActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val onBack: () -> Unit = { finish() }
        Log.d("AddNewActivityActivity", "onCreate")

        setContent {
            FINAL_PROJECTTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("add new activity") },
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
                    AddNewActivityActivityScreen(
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

@Composable
fun AddNewActivityActivityScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .align(Alignment.TopCenter),
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "print name of an activity",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    singleLine = true,
                    label = { Text("name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (name.isNotBlank()) {
                            Log.d("AddNewActivityActivity", "onClick")
                            viewModel.addActivity(name)
                            onBack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Save")
                }
            }
        }
    }
}