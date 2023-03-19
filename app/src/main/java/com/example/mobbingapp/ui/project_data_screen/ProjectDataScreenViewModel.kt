package com.example.mobbingapp.ui.project_data_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobbingapp.database.models.Project
import com.example.mobbingapp.database.models.Ticket
import com.example.mobbingapp.database.models.TicketPhase
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
class ProjectDataScreenViewModel @Inject constructor(
    private val repository: ProjectRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var project: Project? = null
    set(value) {
        field = value
        value?.timeCreated?.let {
            dateCreated = DateStuff.getDateCreated(it)
        }
    }

    var ticketsInFinished by mutableStateOf<List<Ticket>>(emptyList())
        private set

    var dateCreated by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val projectId = savedStateHandle.get<Int>("projectId")
        if (projectId != -1 && projectId != null) {
            viewModelScope.launch {
                val project: Project? = repository.getProjectById(projectId)
                if (project != null) {
                    this@ProjectDataScreenViewModel.project = project
                    getTicketsInFinished()
                } else {
                    TODO("Handle error")
                }
            }
        } else {
            TODO("Handle error")
        }
    }

    private fun getTicketsInFinished() {
        val finishedTickets = mutableListOf<Ticket>()
        project?.let {
            it.tickets.forEach { ticket ->
                if (ticket.phaseData.currentPhase == TicketPhase.FINISHED) {
                    finishedTickets.add(ticket)
                }
            }
        }
        ticketsInFinished = finishedTickets
    }

    fun onEvent(event: ProjectDataScreenEvent) {
        when (event) {
            is ProjectDataScreenEvent.OnBackPressed -> {
                sendUiEvent(UiEvent.Navigate(Routes.PROJECT_SETTINGS + "?projectId=${project?.id}"))
            }
            is ProjectDataScreenEvent.OnTicketPressed -> {
                sendUiEvent(UiEvent.Navigate(Routes.TICKET_DATA + "?projectId=${project?.id}&ticketId=${event.ticketUUID}"))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}