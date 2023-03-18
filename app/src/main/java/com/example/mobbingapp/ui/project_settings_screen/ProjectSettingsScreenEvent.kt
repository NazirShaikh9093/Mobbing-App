package com.example.mobbingapp.ui.project_settings_screen

sealed class ProjectSettingsScreenEvent {
    object OnEditPressed: ProjectSettingsScreenEvent()
    object OnDataPressed: ProjectSettingsScreenEvent()
    object OnDeletePressed: ProjectSettingsScreenEvent()
    object OnBackPressed: ProjectSettingsScreenEvent()
    object OnDeleteConfirmPressed: ProjectSettingsScreenEvent()
    object OnDeleteConfirmationDialogDismissed: ProjectSettingsScreenEvent()
}
