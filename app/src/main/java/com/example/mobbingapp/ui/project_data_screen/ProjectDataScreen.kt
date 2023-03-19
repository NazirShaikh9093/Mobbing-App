package com.example.mobbingapp.ui.project_data_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobbingapp.ui.tickets_screen.TicketItem
import com.example.mobbingapp.util.UiEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProjectDataScreen(
    onNavigation: (UiEvent.Navigate) -> Unit,
    viewModel: ProjectDataScreenViewModel = hiltViewModel()
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
    val gridState = rememberLazyGridState()

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back arrow",
                    modifier = Modifier
                        .size(60.dp)
                        .clickable { viewModel.onEvent(ProjectDataScreenEvent.OnBackPressed) }
                )
                Text(
                    text = "Project data",
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(60.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Ticket history:", fontSize = 26.sp)
            Spacer(modifier = Modifier.height(8.dp))
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Adaptive(150.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .border(1.dp, Color.White)
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(viewModel.ticketsInFinished) {
                    TicketItem(
                        name = it.name,
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(60.dp)
                            .clickable {
                                viewModel.onEvent(ProjectDataScreenEvent.OnTicketPressed(it.uuid))
                            },
                        textSize = 20.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Date created:", fontSize = 20.sp)
                Text(text = viewModel.dateCreated, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}