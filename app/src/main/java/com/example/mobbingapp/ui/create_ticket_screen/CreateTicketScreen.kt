package com.example.mobbingapp.ui.create_ticket_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobbingapp.util.UiEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateTicketScreen(
    onNavigation: (UiEvent.Navigate) -> Unit,
    viewModel: CreateTicketScreenViewModel = hiltViewModel()
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
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
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
                        .clickable { viewModel.onEvent(CreateTicketScreenEvent.OnBackPressed) }
                )
                Text(
                    text = "Ticket Creation",
                    fontSize = 40.sp
                )
                Spacer(modifier = Modifier.width(60.dp))
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Ticket name:")
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.ticketName,
                onValueChange = {
                    viewModel.ticketName = it
                },
                singleLine = true,
                placeholder = {
                    Text(text = "Enter a name")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Notes:")
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.ticketNotes,
                onValueChange = {
                    viewModel.ticketNotes = it
                },
                placeholder = {
                    Text(text = "Enter some information about the ticket!")
                },
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.onEvent(CreateTicketScreenEvent.OnCreatePressed)
                },
                shape = CircleShape,
                modifier = Modifier
                    .width(130.dp)
                    .height(50.dp)
            ) {
                Text(text = "Create", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}