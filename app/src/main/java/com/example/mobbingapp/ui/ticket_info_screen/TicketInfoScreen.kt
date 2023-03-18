package com.example.mobbingapp.ui.ticket_info_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobbingapp.util.UiEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TicketInfoScreen(
    onNavigation: (UiEvent.Navigate) -> Unit,
    viewModel: TicketInfoScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Navigate -> {
                    onNavigation(it)
                }
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(it.message)
                }
                else -> Unit
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
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
                        .clickable { viewModel.onEvent(TicketInfoScreenEvent.OnBackPressed) }
                )
                Text(
                    text = viewModel.ticketName,
                    fontSize = 40.sp
                )
                Spacer(modifier = Modifier.width(60.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Ticket notes", fontSize = 30.sp)
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                modifier = Modifier.weight(1f).fillMaxWidth().padding(8.dp,0.dp),
                value = viewModel.ticketNotes,
                onValueChange = {
                    viewModel.ticketNotes = it
                },
                placeholder = {
                    Text(text = "Enter some information about the ticket!")
                }
            )

            Spacer(modifier = Modifier.height(20.dp))
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .weight(1f)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Is everyone ready to move onto the next phase?",
                        softWrap = true,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp, 0.dp),
                    )
                    Switch(modifier = Modifier
                        .scale(2f)
                        .padding(20.dp),
                        checked = viewModel.canMoveOn, onCheckedChange = {
                            viewModel.canMoveOn = it
                        })
                }
            }
            Button(
                onClick = {
                    viewModel.onEvent(TicketInfoScreenEvent.OnMoveToNextPhasePressed)
                },
                shape = CircleShape,
                modifier = Modifier
                    .width(260.dp)
                    .height(60.dp)
                    .weight(1f)
                    .wrapContentHeight()
            ) {
                Text(
                    text = "Move onto next phase",
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}