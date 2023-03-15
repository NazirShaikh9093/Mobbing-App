package com.example.myapplication.ui.project_create_or_amend

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.database.models.Project
import com.example.myapplication.database.repositories.ProjectRepository
import com.example.myapplication.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectCreateOrAmendViewModel @Inject constructor(
    private val repository: ProjectRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(

) {
    var project by mutableStateOf<Project?>(null)
        private set

    var projectName by mutableStateOf("")
        private set

    var imageUUID by mutableStateOf("")
        private set

    var teamMembers by mutableStateOf<List<String>>(emptyList())
        private set

    var screenTitle by mutableStateOf("")
        private set

    var createOrSaveText by mutableStateOf("")
        private set

    private var isCreating: Boolean? = null
        private set(value) {
            field = value
            if (value == true) {
                screenTitle = "Project creation"
                createOrSaveText = "Create"
            } else {
                screenTitle = "Manage project"
                createOrSaveText = "Save"
            }
        }

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val projectId = savedStateHandle.get<Int>("projectId")
        if (projectId != -1 && projectId != null) {
            viewModelScope.launch {
                val project: Project? = repository.getProjectById(projectId)
                if (project != null) {
                    projectName = project.name
                    imageUUID = project.imageUUID
                    teamMembers = project.teamMembers
                    this@ProjectCreateOrAmendViewModel.project = project
                    isCreating = false
                } else {
                    isCreating = true
                }
            }
        } else {
            isCreating = true
        }
    }

    fun onEvent(event: ProjectCreateOrAmendEvent) {
        when (event) {
            is ProjectCreateOrAmendEvent.OnNameChanged -> {
                projectName = event.name
            }
            is ProjectCreateOrAmendEvent.OnCoverImageChanged -> {
                imageUUID = event.imageUUID
            }
            is ProjectCreateOrAmendEvent.OnTeamMemberDeleted -> {
                teamMembers = teamMembers.filter {
                    it != event.teamMember
                }
            }
            is ProjectCreateOrAmendEvent.OnTeamMemberAdded -> {
                teamMembers = teamMembers.plus(event.teamMember)
            }
            is ProjectCreateOrAmendEvent.OnBackPressed -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is ProjectCreateOrAmendEvent.OnCreateOrSavePressed -> {
                viewModelScope.launch {
                    repository.insertOrReplaceProject(
                        project?.copy(
                            name = projectName,
                            imageUUID = imageUUID,
                            teamMembers = teamMembers
                        ) ?: Project(
                            name = projectName,
                            imageUUID = imageUUID,
                            teamMembers = teamMembers,
                            tickets = emptyList()
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}