package com.vina_esima.final_project.mainactivity.items

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vina_esima.final_project.ui.theme.ThemeManager

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
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
                checked = ThemeManager.darkMode,
                onCheckedChange = { ThemeManager.darkMode = it }
            )
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
