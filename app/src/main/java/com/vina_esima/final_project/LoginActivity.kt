package com.vina_esima.final_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.vina_esima.final_project.ui.theme.FINAL_PROJECTTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FINAL_PROJECTTheme {

                val goToMainActivity: () -> Unit = {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                Scaffold { padding ->
                    LoginActivityScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        goToMainActivity = goToMainActivity
                    )
                }
            }
        }
    }
}

@Composable
fun LoginActivityScreen(
    modifier: Modifier = Modifier,
    goToMainActivity: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showErrorDialog by remember { mutableStateOf(false) }

    val auth = Firebase.auth

    if (auth.currentUser != null) {
        goToMainActivity()
        return
    }

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it.trim() },
                    singleLine = true,
                    label = { Text("Username (e‑mail)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))

                AuthButton(text = "Register") {
                    auth.createUserWithEmailAndPassword(username, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                goToMainActivity()
                            } else {
                                showErrorDialog = true
                                errorMessage = task.exception?.localizedMessage
                                Log.d("LoginActivity", "Authentication failed. Error: ${'$'}{task.exception}")
                            }
                        }
                }

                Spacer(Modifier.height(12.dp))

                AuthButton(text = "Login") {
                    auth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                goToMainActivity()
                            } else {
                                showErrorDialog = true
                                errorMessage = task.exception?.localizedMessage
                                Log.d("LoginActivity", "Authentication failed. Error: ${'$'}{task.exception}")
                            }
                        }
                }

                errorMessage?.let { msg ->
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = msg,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("Authentication error") },
                text = {
                    Text("Invalid username or password. Please try again.")
                },
                confirmButton = {
                    TextButton(onClick = { showErrorDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Composable
private fun AuthButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(text)
    }
}
