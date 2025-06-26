package com.vina_esima.final_project.mainactivity.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreenColumn(
    modifier: Modifier = Modifier,
    onClickToStartActivityActivity: () -> Unit,
    onClickToAddNewActivityActivity: () -> Unit,
    onClickToDeleteActivityActivity: () ->  Unit
) {
    LazyColumn (
        modifier = modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        val list = listOf(
            Pair("start activity", onClickToStartActivityActivity),
            Pair("add new activity", onClickToAddNewActivityActivity),
            Pair("add existing entry", onClickToDeleteActivityActivity)
        )

        items(list.size) {
            Card(
                modifier = Modifier.padding(8.dp)
                    .fillMaxSize()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                onClick = {
                    list[it].second()
                },
                shape = RoundedCornerShape(16.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = list[it].first,
                        textAlign = TextAlign.Center,
                        fontSize = 22.sp
                    )
                }
            }
        }
    }
}
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onClickToStartActivityActivity: () -> Unit,
    onClickToAddNewActivityActivity: () -> Unit,
    onClickToDeleteActivityActivity: () -> Unit
) {
    MainScreenColumn(modifier,
        onClickToStartActivityActivity,
        onClickToAddNewActivityActivity,
        onClickToDeleteActivityActivity)
}