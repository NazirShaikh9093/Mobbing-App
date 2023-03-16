package com.example.mobbingapp.ui.ticket_screen

import com.example.mobbingapp.database.models.Ticket

sealed class TicketScreenEvent {
    object OnBackPressed: TicketScreenEvent()
    object OnAddTicketPressed: TicketScreenEvent()
    object OnSettingsPressed: TicketScreenEvent()
    data class OnTicketPressed(val ticket: Ticket): TicketScreenEvent()
}
