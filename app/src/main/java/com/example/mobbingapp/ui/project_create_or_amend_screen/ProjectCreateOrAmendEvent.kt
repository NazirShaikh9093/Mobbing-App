package com.example.mobbingapp.ui.project_create_or_amend_screen

sealed class ProjectCreateOrAmendEvent {
    data class OnNameChanged(val name: String) : ProjectCreateOrAmendEvent()
    data class OnCoverImageChanged(val imageUUID: String): ProjectCreateOrAmendEvent()
    data class OnTeamMemberAdded(val teamMember: String): ProjectCreateOrAmendEvent()
    data class OnTeamMemberDeleted(val teamMember: String): ProjectCreateOrAmendEvent()
    object OnBackPressed: ProjectCreateOrAmendEvent()
    object OnCreateOrSavePressed: ProjectCreateOrAmendEvent()
}
