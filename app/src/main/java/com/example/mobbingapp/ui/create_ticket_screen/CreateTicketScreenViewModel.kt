package com.example.mobbingapp.ui.create_ticket_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobbingapp.database.models.Project
import com.example.mobbingapp.database.models.Ticket
import com.example.mobbingapp.database.repositories.ProjectRepository
import com.example.mobbingapp.util.Routes
import com.example.mobbingapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateTicketScreenViewModel @Inject constructor(
    private val repository: ProjectRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var project: Project? = null

    var ticketName by mutableStateOf("")

    var ticketNotes by mutableStateOf("")

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val projectId = savedStateHandle.get<Int>("projectId")
        if (projectId != -1 && projectId != null) {
            viewModelScope.launch {
                val project: Project? = repository.getProjectById(projectId)
                if (project != null) {
                    this@CreateTicketScreenViewModel.project = project
                } else {
                    TODO("Handle error")
                }
            }
        } else {
            TODO("Handle error")
        }
    }

    fun onEvent(event: CreateTicketScreenEvent) {
        when (event) {
            is CreateTicketScreenEvent.OnBackPressed -> {
                sendUiEvent(UiEvent.Navigate(Routes.TICKET_BOARD + "?projectId=${project?.id}"))
            }
            is CreateTicketScreenEvent.OnCreatePressed -> {
                saveNewTicket()
                sendUiEvent(UiEvent.Navigate(Routes.TICKET_BOARD + "?projectId=${project?.id}"))
            }
        }
    }

    private fun saveNewTicket() {
        val newTicket = Ticket(
            name = ticketName,
            notes = ticketNotes,
            uuid = UUID.randomUUID().toString()
        )
        val projectTickets = project?.tickets
        val listOfTicketsToInsert = mutableListOf<Ticket>()
        projectTickets?.forEach {
            listOfTicketsToInsert.add(it)
        }
        listOfTicketsToInsert.add(
            newTicket
        )
        viewModelScope.launch {
            project?.copy(tickets = listOfTicketsToInsert)?.let {
                repository.insertOrReplaceProject(it)
            } ?: TODO("Handle when there is no project")
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}