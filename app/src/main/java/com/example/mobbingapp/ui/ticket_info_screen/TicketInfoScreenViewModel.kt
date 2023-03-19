package com.example.mobbingapp.ui.ticket_info_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobbingapp.database.models.PhaseData
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
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TicketInfoScreenViewModel @Inject constructor(
    private val repository: ProjectRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var project: Project? = null
    private var ticket: Ticket? = null
    set(value) {
        field = value
        if (value != null) {
            ticketName = value.name
            canMoveOn = value.canMoveOn
            ticketNotes = value.notes
        }
    }

    var ticketName by mutableStateOf("")

    var canMoveOn by mutableStateOf(false)

    var ticketNotes by mutableStateOf("")

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val projectId = savedStateHandle.get<Int>("projectId")
        val ticketId = savedStateHandle.get<String>("ticketId")
        if ((projectId != -1 && projectId != null) && (ticketId != "-1" && ticketId != null)) {
            viewModelScope.launch {
                val project: Project? = repository.getProjectById(projectId)
                if (project != null) {
                    this@TicketInfoScreenViewModel.project = project
                    val ticket = project.tickets.find {
                        it.uuid == ticketId
                    } ?: TODO("Handle in case no ticket id match")
                    this@TicketInfoScreenViewModel.ticket = ticket
                } else {
                    TODO("Handle error")
                }
            }
        } else {
            TODO("Handle error")
        }
    }

    fun onEvent(event: TicketInfoScreenEvent) {
        when (event) {
            is TicketInfoScreenEvent.OnBackPressed -> {
                saveIfChangedOrNextPressed()
                sendUiEvent(UiEvent.Navigate(Routes.TICKET_BOARD + "?projectId=${project?.id}"))
            }
            is TicketInfoScreenEvent.OnCanMoveOnCheckChanged -> {
                canMoveOn = event.isChecked
            }
            is TicketInfoScreenEvent.OnMoveToNextPhasePressed -> {
                if (canMoveOn) {
                    saveIfChangedOrNextPressed(true)
                    sendUiEvent(UiEvent.Navigate(Routes.TICKET_BOARD + "?projectId=${project?.id}"))
                } else {
                    sendUiEvent(UiEvent.ShowSnackbar("Everyone must agree to continue before moving on!"))
                }
            }
        }
    }

    private fun saveIfChangedOrNextPressed(nextPhasePressed: Boolean = false) {
        project?.let { nonNullProject ->
            ticket?.let { nonNullTicket ->
                if (nonNullTicket.canMoveOn != canMoveOn || nonNullTicket.notes != ticketNotes || nextPhasePressed) {
                    val updatedTicketList = nonNullProject.tickets.map {
                        if (it.uuid == nonNullTicket.uuid) {
                            it.copy(
                                phaseData = if (nextPhasePressed) {
                                    canMoveOn = false
                                    getPhaseDataForNextPhase(nonNullTicket)
                                } else {
                                    it.phaseData
                                },
                                notes = ticketNotes,
                                canMoveOn = canMoveOn
                            )
                        } else {
                            it
                        }
                    }
                    val projectToBeSaved = nonNullProject.copy(
                        tickets = updatedTicketList
                    )
                    viewModelScope.launch {
                        repository.insertOrReplaceProject(projectToBeSaved)
                    }
                }
            }
        }
    }

    private fun getPhaseDataForNextPhase(ticket: Ticket): PhaseData {
        val phaseData = ticket.phaseData
        return when (phaseData.currentPhase) {
            TicketPhase.EMPATHISE -> {
                phaseData.copy(
                    currentPhase = TicketPhase.DEFINE,
                    empathiseCompletedTime = Calendar.getInstance(),
                    timeInEmpathise = DateStuff.compareDateToCurrent(ticket.dateCreated)
                )
            }
            TicketPhase.DEFINE -> {
                phaseData.copy(
                    currentPhase = TicketPhase.IDEATE,
                    defineCompletedTime = Calendar.getInstance(),
                    timeInDefine = DateStuff.compareDateToCurrent(phaseData.empathiseCompletedTime!!)
                )
            }
            TicketPhase.IDEATE -> {
                phaseData.copy(
                    currentPhase = TicketPhase.PROTOTYPE,
                    ideateCompletedTime = Calendar.getInstance(),
                    timeInIdeate = DateStuff.compareDateToCurrent(phaseData.defineCompletedTime!!)
                )
            }
            TicketPhase.PROTOTYPE -> {
                phaseData.copy(
                    currentPhase = TicketPhase.TEST,
                    prototypeCompletedTime = Calendar.getInstance(),
                    timeInPrototype = DateStuff.compareDateToCurrent(phaseData.ideateCompletedTime!!)
                )
            }
            TicketPhase.TEST -> {
                phaseData.copy(
                    currentPhase = TicketPhase.FINISHED,
                    testCompletedTime = Calendar.getInstance(),
                    timeInTest = DateStuff.compareDateToCurrent(phaseData.prototypeCompletedTime!!)
                )
            }
            else -> phaseData
        }
    }


    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}