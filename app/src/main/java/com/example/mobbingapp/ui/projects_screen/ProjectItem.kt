package com.example.mobbingapp.ui.projects_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProjectItem(name: String, imageUUID: String) {
    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(context.filesDir.resolve(imageUUID))
    Column(modifier = Modifier, verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painter,
            contentDescription = name,
            modifier = Modifier
                .size(180.dp)
                .border(1.dp, Color.White)
        )
        Text(text = name, color = Color.White)
    }
}