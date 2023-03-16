package com.example.mobbingapp.ui.ticket_screen

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
import com.example.mobbingapp.util.Routes
import com.example.mobbingapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketScreenViewModel @Inject constructor(
    private val repository: ProjectRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var project by mutableStateOf<Project?>(null)
        private set

    var ticketsInEmpathise by mutableStateOf<List<Ticket>>(emptyList())
        private set

    var ticketsInDefine by mutableStateOf<List<Ticket>>(emptyList())
        private set

    var ticketsInIdeate by mutableStateOf<List<Ticket>>(emptyList())
        private set

    var ticketsInPrototype by mutableStateOf<List<Ticket>>(emptyList())
        private set

    var ticketsInTest by mutableStateOf<List<Ticket>>(emptyList())
        private set

    private var tickets: List<Ticket> = emptyList()
        private set(value) {
            field = value
            filterTicketsIntoPhases(value)
        }

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val projectId = savedStateHandle.get<Int>("projectId")
        if (projectId != -1 && projectId != null) {
            viewModelScope.launch {
                val project: Project? = repository.getProjectById(projectId)
                if (project != null) {
                    this@TicketScreenViewModel.project = project
                    tickets = project.tickets
                } else {
                    TODO("Handle error")
                }
            }
        } else {
            TODO("Handle error")
        }
    }

    private fun filterTicketsIntoPhases(value: List<Ticket>) {
        val ticketsInEmpathise = mutableListOf<Ticket>()
        val ticketsInDefine = mutableListOf<Ticket>()
        val ticketsInIdeate = mutableListOf<Ticket>()
        val ticketsInPrototype = mutableListOf<Ticket>()
        val ticketsInTest = mutableListOf<Ticket>()

        value.forEach {
            when (it.phaseData.currentPhase) {
                TicketPhase.EMPATHISE -> ticketsInEmpathise.add(it)
                TicketPhase.DEFINE -> ticketsInDefine.add(it)
                TicketPhase.IDEATE -> ticketsInIdeate.add(it)
                TicketPhase.PROTOTYPE -> ticketsInPrototype.add(it)
                TicketPhase.TEST -> ticketsInTest.add(it)
                else -> Unit
            }
        }

        this.ticketsInEmpathise = ticketsInEmpathise
        this.ticketsInDefine = ticketsInDefine
        this.ticketsInIdeate = ticketsInIdeate
        this.ticketsInPrototype = ticketsInPrototype
        this.ticketsInTest = ticketsInTest
    }

    fun onEvent(event: TicketScreenEvent) {
        when (event) {
            is TicketScreenEvent.OnAddTicketPressed -> {
                sendUiEvent(UiEvent.Navigate(Routes.TICKET_CREATION + "?projectId=${project?.id}"))
            }
            is TicketScreenEvent.OnBackPressed -> {
                sendUiEvent(UiEvent.Navigate(Routes.PROJECT_SELECTION))
            }
            is TicketScreenEvent.OnSettingsPressed -> {
                sendUiEvent(UiEvent.Navigate(Routes.PROJECT_SETTINGS + "?projectId=${project?.id}"))
            }
            is TicketScreenEvent.OnTicketPressed -> {
                sendUiEvent(UiEvent.Navigate(Routes.TICKET_VIEW + "?projectId=${project?.id}&ticketId=${event.ticket.uuid}"))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
