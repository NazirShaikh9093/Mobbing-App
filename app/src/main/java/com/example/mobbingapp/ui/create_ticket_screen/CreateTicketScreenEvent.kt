package com.example.mobbingapp.ui.create_ticket_screen

sealed class CreateTicketScreenEvent {
    object OnBackPressed: CreateTicketScreenEvent()
    object OnCreatePressed: CreateTicketScreenEvent()
}
