package com.example.mobbingapp.ui.tickets_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun TicketItem(name: String, modifier: Modifier, textSize: TextUnit) {
    Card(
        modifier = modifier,
        shape = CircleShape
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                fontSize = textSize,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                softWrap = true,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp, 0.dp)
            )
        }
    }
}