package com.example.mobbingapp.ui.project_settings_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConfirmDeleteAlertDialog(
    onDismissed: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.wrapContentHeight(),
        onDismissRequest = { onDismissed.invoke() },
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Are you sure you want to delete this project?",
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center,
                    softWrap = true
                )
            }
        },
        buttons = {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        onDismissed.invoke()
                    },
                    shape = CircleShape,
                    modifier = Modifier
                        .width(130.dp)
                        .height(50.dp)
                ) {
                    Text(text = "No", fontSize = 20.sp)
                }
                Button(
                    onClick = {
                        onConfirm.invoke()
                    },
                    shape = CircleShape,
                    modifier = Modifier
                        .width(130.dp)
                        .height(50.dp)
                ) {
                    Text(text = "Yes", fontSize = 20.sp)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    )
}