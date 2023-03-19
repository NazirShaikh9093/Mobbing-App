package com.example.mobbingapp.ui.tickets_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobbingapp.util.UiEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TicketsScreen(
    onNavigation: (UiEvent.Navigate) -> Unit,
    viewModel: TicketsScreenViewModel = hiltViewModel()
) {
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

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(TicketsScreenEvent.OnAddTicketsPressed)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back arrow",
                    modifier = Modifier
                        .size(60.dp)
                        .weight(1f)
                        .clickable {
                            viewModel.onEvent(TicketsScreenEvent.OnBackPressed)
                        }
                )
                Text(
                    text = viewModel.project?.name ?: "Null string",
                    color = Color.White,
                    modifier = Modifier
                        .padding(10.dp),
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings icon",
                    modifier = Modifier
                        .size(40.dp)
                        .weight(1f)
                        .clickable {
                            viewModel.onEvent(TicketsScreenEvent.OnSettingsPressed)
                        }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Empathise", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            ScrollableTicketRow(tickets = viewModel.ticketsInEmpathise, onTicketPressed = { viewModel.onEvent(TicketsScreenEvent.OnTicketPressed(it)) })
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Define", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            ScrollableTicketRow(tickets = viewModel.ticketsInDefine, onTicketPressed = { viewModel.onEvent(TicketsScreenEvent.OnTicketPressed(it)) })
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Ideate", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            ScrollableTicketRow(tickets = viewModel.ticketsInIdeate, onTicketPressed = { viewModel.onEvent(TicketsScreenEvent.OnTicketPressed(it)) })
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Prototype", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            ScrollableTicketRow(tickets = viewModel.ticketsInPrototype, onTicketPressed = { viewModel.onEvent(TicketsScreenEvent.OnTicketPressed(it)) })
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Test", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            ScrollableTicketRow(tickets = viewModel.ticketsInTest, onTicketPressed = { viewModel.onEvent(TicketsScreenEvent.OnTicketPressed(it)) })
        }
    }
}