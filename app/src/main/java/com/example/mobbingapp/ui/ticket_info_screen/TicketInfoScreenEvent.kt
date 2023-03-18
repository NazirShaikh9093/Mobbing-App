package com.example.mobbingapp.ui.ticket_info_screen

sealed class TicketInfoScreenEvent {
    object OnBackPressed: TicketInfoScreenEvent()
    data class OnCanMoveOnCheckChanged(val isChecked: Boolean): TicketInfoScreenEvent()
    object OnMoveToNextPhasePressed: TicketInfoScreenEvent()
}
