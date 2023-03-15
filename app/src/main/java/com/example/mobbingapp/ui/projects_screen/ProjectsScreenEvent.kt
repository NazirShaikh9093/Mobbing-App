package com.example.mobbingapp.ui.projects_screen

import com.example.mobbingapp.database.models.Project

sealed class ProjectsScreenEvent {
    data class OnProjectSelection(val project: Project): ProjectsScreenEvent()
    object AddNewProject: ProjectsScreenEvent()
}
