package com.vina_esima.final_project.mainactivity.items

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnalyticsScreen(
    modifier: Modifier = Modifier,
    onGoToViewAnalyticsActivity: (year: Int, month: Int, day: Int) -> Unit
) {
    val currentDate = LocalDate.now()

    var selectedYear by remember { mutableStateOf(currentDate.year) }
    var selectedMonth by remember { mutableStateOf(currentDate.monthValue) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Year:",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { selectedYear-- }) {
                    Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous year")
                }
                Text(
                    text = "$selectedYear",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                IconButton(onClick = { selectedYear++ }) {
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next year")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Month:",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = {
                    selectedMonth--
                    if (selectedMonth < 1) {
                        selectedMonth = 12
                        selectedYear--
                    }
                }) {
                    Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous month")
                }
                Text(
                    text = Month.of(selectedMonth).getDisplayName(TextStyle.FULL, Locale.getDefault()),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                IconButton(onClick = {
                    selectedMonth++
                    if (selectedMonth > 12) {
                        selectedMonth = 1
                        selectedYear++
                    }
                }) {
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next month")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            MonthCalendarView(
                year = selectedYear,
                month = selectedMonth,
                onGoToViewAnalyticsActivity = onGoToViewAnalyticsActivity,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthCalendarView(
    year: Int,
    month: Int,
    modifier: Modifier = Modifier,
    onGoToViewAnalyticsActivity: (year: Int, month: Int, day: Int) -> Unit
) {
    val weeks = getWeeksInMonth(year, month)
    val dayNames = remember {
        listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    }

    Column(modifier = modifier) {
        DayNamesHeader(dayNames = dayNames)
        Spacer(modifier = Modifier.height(8.dp))

        weeks.forEach { week ->
            WeekRow(
                week = week,
                month = month,
                year = year,
                onGoToViewAnalyticsActivity = onGoToViewAnalyticsActivity
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun DayNamesHeader(
    dayNames: List<String>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(dayNames) { dayName ->
            Box(modifier = Modifier.aspectRatio(1f)) {
                Text(
                    text = dayName,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekRow(
    week: List<Int>,
    month: Int,
    year: Int,
    onGoToViewAnalyticsActivity: (year: Int, month: Int, day: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val today = LocalDate.now()

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(week) { day ->
            val isToday = day > 0 && year == today.year && month == today.monthValue && day == today.dayOfMonth
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = when {
                        day == 0 -> Color.Transparent
                        isToday -> MaterialTheme.colorScheme.primaryContainer
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    }
                ),
                modifier = Modifier
                    .aspectRatio(1f)
                    .clickable(enabled = day > 0) {
                        if (day > 0) onGoToViewAnalyticsActivity(year, month, day)
                    }
            ) {
                if (day > 0) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = day.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getWeeksInMonth(year: Int, month: Int): List<List<Int>> {
    val yearMonth = YearMonth.of(year, month)
    val firstDayOfMonth = yearMonth.atDay(1)
    val lastDayOfMonth = yearMonth.atEndOfMonth()

    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
    val totalDays = lastDayOfMonth.dayOfMonth

    val weeks = mutableListOf<List<Int>>()
    var currentWeek = mutableListOf<Int>()

    repeat(firstDayOfWeek) {
        currentWeek.add(0)
    }

    for (day in 1..totalDays) {
        currentWeek.add(day)
        if (currentWeek.size == 7) {
            weeks.add(currentWeek)
            currentWeek = mutableListOf()
        }
    }

    if (currentWeek.isNotEmpty()) {
        while (currentWeek.size < 7) {
            currentWeek.add(0)
        }
        weeks.add(currentWeek)
    }

    return weeks
}
