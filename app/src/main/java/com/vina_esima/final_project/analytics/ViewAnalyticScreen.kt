package com.vina_esima.final_project.analytics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vina_esima.final_project.R
import com.vina_esima.final_project.analytics.EnrtyDB.EntryModel

private fun date.toSeconds(): Int = hour * 3600 + minute * 60 + second

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewAnalyticsScreen(
    entries: List<EntryModel>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sortedEntries = remember(entries) {
        entries.groupBy { it.activity }
            .mapValues { (_, list) -> list.sumOf { (it.end.toSeconds() - it.start.toSeconds()).coerceAtLeast(0) } }
            .entries.sortedByDescending { it.value }
    }

    val palette = listOf(
        Color(0xFF4CAF50), Color(0xFFFFC107), Color(0xFFF44336), Color(0xFF2196F3),
        Color(0xFF9C27B0), Color(0xFFFF9800), Color(0xFF00BCD4), Color(0xFF8BC34A),
        Color(0xFF795548), Color(0xFFE91E63)
    )
    val activityColors = remember(sortedEntries) {
        sortedEntries.mapIndexed { i, e -> e.key to palette[i % palette.size] }.toMap()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Daily Analytics") }, navigationIcon = {
                IconButton(onBack) { Icon(painterResource(R.drawable.ic_back_black), null) }
            })
        }
    ) { p ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(p)
                .padding(horizontal = 16.dp)
        ) {
            PieChart(
                entries = sortedEntries,
                colors = activityColors,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(1f)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(12.dp))
            ActivityLegend(sortedEntries, activityColors)
            Spacer(Modifier.height(12.dp))
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(sortedEntries) { (act, dur) -> AnalyticsCard(act, dur) }
            }
        }
    }
}

@Composable
private fun PieChart(entries: List<Map.Entry<String, Int>>, colors: Map<String, Color>, modifier: Modifier) {
    val total = entries.sumOf { it.value }.takeIf { it > 0 } ?: return
    Canvas(modifier) {
        var start = -90f
        entries.forEach { (a, v) ->
            val sweep = v.toFloat() / total * 360f
            drawArc(colors[a] ?: Color.Gray, start, sweep, true, size = size)
            start += sweep
        }
    }
}

@Composable
private fun ActivityLegend(entries: List<Map.Entry<String, Int>>, colors: Map<String, Color>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        entries.forEach { (activity, _) ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(colors[activity] ?: Color.Gray)
                )
                Spacer(Modifier.width(4.dp))
                Text(activity, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun AnalyticsCard(activity: String, durationSec: Int) {
    val hours = durationSec / 3600f
    val bg = when {
        hours < 1f -> Color(0xFFC8E6C9)
        hours < 2f -> Color(0xFFFFF9C4)
        else -> Color(0xFFFFCDD2)
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(bg)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(activity, style = MaterialTheme.typography.titleMedium, color = Color.Black)
            Text(
                String.format("%02d:%02d:%02d", durationSec / 3600, (durationSec % 3600) / 60, durationSec % 60),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}
