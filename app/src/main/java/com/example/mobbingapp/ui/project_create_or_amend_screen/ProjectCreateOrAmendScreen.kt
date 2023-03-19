package com.example.mobbingapp.ui.project_create_or_amend_screen

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mobbingapp.R
import com.example.mobbingapp.util.UiEvent
import java.util.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProjectCreateOrAmendScreen(
    onPopBackStack: () -> Unit,
    viewModel: ProjectCreateOrAmendViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack.invoke()
                }
                else -> Unit
            }
        }
    }


    val scaffoldState = rememberScaffoldState()
    val painter = rememberAsyncImagePainter(
        if (viewModel.imageUUID != "") {
            context.filesDir.resolve(viewModel.imageUUID)
        } else {
            R.drawable.ic_upload
        }
    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val input = context.contentResolver.openInputStream(uri) ?: return@rememberLauncherForActivityResult
            val uuid = UUID.randomUUID().toString()
            val outputFile = context.filesDir.resolve(uuid)
            input.copyTo(outputFile.outputStream())
            viewModel.onEvent(ProjectCreateOrAmendEvent.OnCoverImageChanged(uuid))
        }
    }

    var newMemberName by remember { mutableStateOf("") }

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = viewModel.screenTitle, color = Color.White,
                fontSize = 40.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                Text(text = "Project name:", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = viewModel.projectName,
                    onValueChange = {
                        viewModel.onEvent(ProjectCreateOrAmendEvent.OnNameChanged(it))
                    },
                    placeholder = {
                        Text(text = "Enter a name")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Cover image (optional):", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Image(
                    painter = painter,
                    contentDescription = "Cover image",
                    modifier = Modifier
                        .size(100.dp)
                        .clickable {
                            launcher.launch("image/*")
                        },
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Team members:", fontSize = 20.sp)
                LazyColumn {
                    items(viewModel.teamMembers) {
                        TeamMemberItem(name = it, onDeletePress = { viewModel.onEvent(ProjectCreateOrAmendEvent.OnTeamMemberDeleted(it)) })
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = newMemberName,
                        onValueChange = {
                            newMemberName = it
                        },
                        placeholder = {
                            Text(text = "Add a new team member!")
                        },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "Add new team member button",
                        modifier = Modifier.size(40.dp)
                            .clickable {
                                viewModel.onEvent(ProjectCreateOrAmendEvent.OnTeamMemberAdded(newMemberName))
                                newMemberName = ""
                            }
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Button(
                        onClick = {
                            viewModel.onEvent(ProjectCreateOrAmendEvent.OnBackPressed)
                        },
                        shape = CircleShape,
                        modifier = Modifier
                            .width(130.dp)
                            .height(50.dp)
                    ) {
                        Text(text = "Back", fontSize = 20.sp)
                    }
                    Button(
                        onClick = {
                            viewModel.onEvent(ProjectCreateOrAmendEvent.OnCreateOrSavePressed)
                        },
                        shape = CircleShape,
                        modifier = Modifier
                            .width(130.dp)
                            .height(50.dp)
                    ) {
                        Text(text = viewModel.createOrSaveText, fontSize = 20.sp)
                    }
                }
            }
        }
    }
}