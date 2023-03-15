package com.example.mobbingapp.ui.projects_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobbingapp.database.repositories.ProjectRepository
import com.example.mobbingapp.util.Routes
import com.example.mobbingapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectsScreenViewModel @Inject constructor(
    private val repository: ProjectRepository
): ViewModel() {

    val projects = repository.getProjects()

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ProjectsScreenEvent) {
        when (event) {
            is ProjectsScreenEvent.OnProjectSelection -> {
                sendUiEvent(UiEvent.Navigate(Routes.PROJECT_TICKET_BOARD + "?projectId=${event.project.id}"))
            }
            is ProjectsScreenEvent.AddNewProject -> {
                sendUiEvent(UiEvent.Navigate(Routes.PROJECT_CREATION_OR_AMENDMENT))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
