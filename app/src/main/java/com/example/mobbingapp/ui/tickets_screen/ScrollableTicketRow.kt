package com.example.mobbingapp.ui.tickets_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobbingapp.database.models.Ticket

@Composable
fun ScrollableTicketRow(
    tickets: List<Ticket>,
    onTicketPressed: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .background(color = Color.LightGray, shape = CircleShape)
            .fillMaxWidth()
            .height(80.dp)
            .padding(8.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tickets) {
            TicketItem(
                name = it.name, modifier = Modifier
                    .width(150.dp)
                    .height(60.dp)
                    .clickable {
                        onTicketPressed(it.uuid)
                    },
                textSize = 20.sp
            )
        }
    }
}