package com.example.mobbingapp.ui.project_settings_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobbingapp.database.models.Project
import com.example.mobbingapp.database.repositories.ProjectRepository
import com.example.mobbingapp.util.Routes
import com.example.mobbingapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectSettingsScreenViewModel @Inject constructor(
    private val repository: ProjectRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var project: Project? = null

    var isDialogShowing by mutableStateOf(false)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val projectId = savedStateHandle.get<Int>("projectId")
        if (projectId != -1 && projectId != null) {
            viewModelScope.launch {
                val project: Project? = repository.getProjectById(projectId)
                if (project != null) {
                    this@ProjectSettingsScreenViewModel.project = project
                } else {
                    TODO("Handle error")
                }
            }
        } else {
            TODO("Handle error")
        }
    }

    fun onEvent(event: ProjectSettingsScreenEvent) {
        when (event) {
            is ProjectSettingsScreenEvent.OnBackPressed -> {
                sendUiEvent(UiEvent.Navigate(Routes.TICKET_BOARD + "?projectId=${project?.id}"))
            }
            is ProjectSettingsScreenEvent.OnDataPressed -> {
                sendUiEvent(UiEvent.Navigate(Routes.PROJECT_DATA + "?projectId=${project?.id}"))
            }
            is ProjectSettingsScreenEvent.OnDeletePressed -> {
                isDialogShowing = true
            }
            is ProjectSettingsScreenEvent.OnDeleteConfirmationDialogDismissed -> {
                isDialogShowing = false
            }
            is ProjectSettingsScreenEvent.OnEditPressed -> {
                sendUiEvent(UiEvent.Navigate(Routes.PROJECT_CREATION_OR_AMENDMENT + "?projectId=${project?.id}"))
            }
            is ProjectSettingsScreenEvent.OnDeleteConfirmPressed -> {
                viewModelScope.launch {
                    project?.let {
                        repository.deleteProject(it)
                    }
                }
                sendUiEvent(UiEvent.Navigate(Routes.PROJECT_SELECTION))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}