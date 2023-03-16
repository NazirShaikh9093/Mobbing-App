package com.example.mobbingapp.ui.ticket_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TicketItem(name: String) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(60.dp),
        shape = CircleShape
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                fontSize = 20.sp,
                color = Color.White,
                maxLines = 2,
                softWrap = true,
                modifier = Modifier.padding(16.dp, 0.dp)
            )
        }
    }
}