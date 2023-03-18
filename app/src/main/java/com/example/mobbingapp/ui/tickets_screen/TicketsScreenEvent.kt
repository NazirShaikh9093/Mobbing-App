package com.example.mobbingapp.ui.tickets_screen

sealed class TicketsScreenEvent {
    object OnBackPressed: TicketsScreenEvent()
    object OnAddTicketsPressed: TicketsScreenEvent()
    object OnSettingsPressed: TicketsScreenEvent()
    data class OnTicketPressed(val ticketUUID: String): TicketsScreenEvent()
}
