
package com.vina_esima.final_project.mainactivity.MainItems

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vina_esima.final_project.ThemeViewModel

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    themeViewModel: ThemeViewModel = ThemeViewModel.instance,
    onLogOut: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Dark theme",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = themeViewModel.isDarkTheme(),
                onCheckedChange = { isChecked ->
                    when {
                        isChecked -> themeViewModel.setThemeMode(ThemeViewModel.ThemeMode.DARK)
                        else -> themeViewModel.setThemeMode(ThemeViewModel.ThemeMode.LIGHT)
                    }
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Theme mode",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )

            val currentMode = themeViewModel.getCurrentThemeMode()
            TextButton(
                onClick = { themeViewModel.toggleTheme() }
            ) {
                Text(
                    text = when (currentMode) {
                        ThemeViewModel.ThemeMode.SYSTEM -> "System"
                        ThemeViewModel.ThemeMode.LIGHT -> "Light"
                        ThemeViewModel.ThemeMode.DARK -> "Dark"
                    }
                )
            }
        }

        Button(
            onClick = onLogOut,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Log Out")
        }
    }
}