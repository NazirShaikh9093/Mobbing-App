package com.example.mobbingapp.ui.project_settings_screen

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobbingapp.util.UiEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProjectSettingsScreen(
    onNavigation: (UiEvent.Navigate) -> Unit,
    viewModel: ProjectSettingsScreenViewModel = hiltViewModel()
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

    Scaffold(scaffoldState = scaffoldState) {
        if (viewModel.isDialogShowing) {
            ConfirmDeleteAlertDialog(
                onDismissed = { viewModel.onEvent(ProjectSettingsScreenEvent.OnDeleteConfirmationDialogDismissed) },
                onConfirm = { viewModel.onEvent(ProjectSettingsScreenEvent.OnDeleteConfirmPressed) }
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(40.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 10.dp),
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
                        .clickable { viewModel.onEvent(ProjectSettingsScreenEvent.OnBackPressed) }
                )
                Text(
                    text = "Project settings",
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(60.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    viewModel.onEvent(ProjectSettingsScreenEvent.OnEditPressed)
                },
                shape = CircleShape,
                modifier = Modifier
                    .width(130.dp)
                    .height(50.dp)
            ) {
                Text(text = "Edit", fontSize = 20.sp, textAlign = TextAlign.Center)
            }

            Button(
                onClick = {
                    viewModel.onEvent(ProjectSettingsScreenEvent.OnDataPressed)
                },
                shape = CircleShape,
                modifier = Modifier
                    .width(130.dp)
                    .height(50.dp)
            ) {
                Text(text = "Data", fontSize = 20.sp, textAlign = TextAlign.Center)
            }

            Button(
                onClick = {
                    viewModel.onEvent(ProjectSettingsScreenEvent.OnDeletePressed)
                },
                shape = CircleShape,
                modifier = Modifier
                    .width(130.dp)
                    .height(50.dp)
            ) {
                Text(text = "Delete", fontSize = 20.sp, textAlign = TextAlign.Center)
            }
        }
    }

}