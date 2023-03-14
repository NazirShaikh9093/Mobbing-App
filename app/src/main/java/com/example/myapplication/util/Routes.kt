package com.example.myapplication.util

import com.example.myapplication.database.models.Project
import com.example.myapplication.database.models.Ticket

object Routes {
    const val PROJECT_SELECTION = "project_selection"
    const val PROJECT_TICKET_BOARD = "project_ticket_board"
    const val PROJECT_CREATION_OR_AMENDMENT = "project_creation_or_amendment"

    val TEST_PROJECTS = listOf(
        Project(
            id = 1, name = "Test 1", coverImage = "abc",
            tickets = listOf(Ticket("Test 1", "Test 1", canMoveOn = false)),
            teamMembers = listOf("Jamie Dickson", "Manuel Monroe", "Elizabeth the Second", "Tommas Edding")
        ),
        Project(
            id = 1, name = "Test 2", coverImage = "abc",
            tickets = listOf(Ticket("Test 2", "Test 2", canMoveOn = false)),
            teamMembers = listOf("Jamie Dickson", "Manuel Monroe", "Elizabeth the Second", "Tommas Edding")
        ),
        Project(
            id = 1, name = "Test 3", coverImage = "abc",
            tickets = listOf(Ticket("Test 3", "Test 3", canMoveOn = false)),
            teamMembers = listOf("Jamie Dickson", "Manuel Monroe", "Elizabeth the Second", "Tommas Edding")
        )
    )
}