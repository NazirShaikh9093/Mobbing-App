package com.example.mobbingapp.ui.project_create_or_amend

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobbingapp.R

@Composable
fun TeamMemberItem(
    onDeletePress: () -> Unit,
    name: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.ic_circle),
            contentDescription = "Circular dot",
            modifier = Modifier
                .size(8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = name, fontSize = 20.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "-", fontSize = 30.sp, fontWeight = FontWeight(800),
            modifier = Modifier.clickable {
                onDeletePress.invoke()
            }
        )
    }
}