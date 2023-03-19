package com.example.mobbingapp.ui.ticket_data_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobbingapp.util.UiEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TicketDataScreen(
    onNavigation: (UiEvent.Navigate) -> Unit,
    viewModel: TicketDataScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Navigate -> {
                    onNavigation(it)
                }
                else -> Unit
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back arrow",
                    modifier = Modifier
                        .size(60.dp)
                        .clickable { viewModel.onEvent(TicketDataScreenEvent.OnBackPressed) }
                )
                Text(
                    text = "Ticket data",
                    fontSize = 40.sp
                )
                Spacer(modifier = Modifier.width(60.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Notes", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = viewModel.ticketNotes,
                onValueChange = {},
                placeholder = {
                    Text(text = "This ticket has no description")
                },
                enabled = false
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Time spent in each phase:", fontSize = 22.sp)
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Empathise")
                Text(text = viewModel.timeSpentInEmpathise)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Define")
                Text(text = viewModel.timeSpentInDefine)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Ideate")
                Text(text = viewModel.timeSpentInIdeate)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Prototype")
                Text(text = viewModel.timeSpentInPrototype)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Test")
                Text(text = viewModel.timeSpentInTest)
            }
            Spacer(modifier = Modifier.height(40.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Date created:", fontSize = 20.sp)
                Text(text = viewModel.dateCreated, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}