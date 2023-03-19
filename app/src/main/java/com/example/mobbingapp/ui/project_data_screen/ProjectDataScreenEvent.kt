package com.example.mobbingapp.ui.project_data_screen

sealed class ProjectDataScreenEvent {
    data class OnTicketPressed(val ticketUUID: String): ProjectDataScreenEvent()
    object OnBackPressed: ProjectDataScreenEvent()
}
