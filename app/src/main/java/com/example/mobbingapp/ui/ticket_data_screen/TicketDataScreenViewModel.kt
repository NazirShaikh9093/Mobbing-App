package com.example.mobbingapp.ui.ticket_data_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobbingapp.database.models.Project
import com.example.mobbingapp.database.models.Ticket
import com.example.mobbingapp.database.repositories.ProjectRepository
import com.example.mobbingapp.util.DateStuff
import com.example.mobbingapp.util.Routes
import com.example.mobbingapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketDataScreenViewModel @Inject constructor(
    private val repository: ProjectRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var project: Project? = null
    private var ticket: Ticket? = null
        set(value) {
            field = value
            value?.phaseData?.let {
                timeSpentInEmpathise = it.timeInEmpathise
                timeSpentInDefine = it.timeInDefine
                timeSpentInIdeate = it.timeInIdeate
                timeSpentInPrototype = it.timeInPrototype
                timeSpentInTest = it.timeInTest
                dateCreated = DateStuff.getDateCreated(value.dateCreated)
                ticketNotes = value.notes
            }
        }

    var ticketNotes by mutableStateOf("")

    var dateCreated by mutableStateOf("")
        private set

    var timeSpentInEmpathise by mutableStateOf("")
        private set

    var timeSpentInDefine by mutableStateOf("")
        private set

    var timeSpentInIdeate by mutableStateOf("")
        private set

    var timeSpentInPrototype by mutableStateOf("")
        private set

    var timeSpentInTest by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val projectId = savedStateHandle.get<Int>("projectId")
        val ticketId = savedStateHandle.get<String>("ticketId")
        if ((projectId != -1 && projectId != null) && (ticketId != "-1" && ticketId != null)) {
            viewModelScope.launch {
                val project: Project? = repository.getProjectById(projectId)
                if (project != null) {
                    this@TicketDataScreenViewModel.project = project
                    val ticket = project.tickets.find {
                        it.uuid == ticketId
                    } ?: TODO("Handle in case no ticket id match")
                    this@TicketDataScreenViewModel.ticket = ticket
                } else {
                    TODO("Handle error")
                }
            }
        } else {
            TODO("Handle error")
        }
    }

    fun onEvent(event: TicketDataScreenEvent) {
        when (event) {
            is TicketDataScreenEvent.OnBackPressed -> {
                sendUiEvent(UiEvent.Navigate(Routes.PROJECT_DATA + "?projectId=${project?.id}"))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}