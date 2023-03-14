package com.example.myapplication.ui.projects_screen

import com.example.myapplication.database.models.Project

sealed class ProjectsScreenEvent {
    data class OnProjectSelection(val project: Project): ProjectsScreenEvent()
    object AddNewProject: ProjectsScreenEvent()
}
