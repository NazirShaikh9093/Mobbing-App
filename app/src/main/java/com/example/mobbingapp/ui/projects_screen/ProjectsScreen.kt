package com.example.mobbingapp.ui.projects_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobbingapp.util.UiEvent


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProjectsScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ProjectsScreenViewModel = hiltViewModel()
) {
    val projects = viewModel.projects.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    val state = rememberLazyGridState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Projects", color = Color.White, modifier = Modifier
                    .padding(10.dp),
                fontSize = 30.sp
            )
            LazyVerticalGrid(
                state = state,
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                items(projects.value) {
                    ProjectItem(name = it.name, imageUUID = it.imageUUID)
                }
                item {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.size(180.dp)
                            .clickable {
                                viewModel.onEvent(ProjectsScreenEvent.AddNewProject)
                            }
                    ) {
                        Image(
                            painter = painterResource(
                                id = com.example.mobbingapp.R.drawable.ic_add
                            ),
                            contentDescription = "Add new project",
                            modifier = Modifier.size(100.dp),
                            colorFilter = ColorFilter.tint(Color.White),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }

}