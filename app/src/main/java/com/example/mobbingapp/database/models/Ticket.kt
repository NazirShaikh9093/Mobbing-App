package com.example.mobbingapp.database.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Ticket(
    val name: String,
    val uuid: String,
    val notes: String,
    val canMoveOn: Boolean = false,
    val phaseData: PhaseData = PhaseData(),
    val dateCreated: Calendar = Calendar.getInstance()
) : Parcelable {

    override fun toString(): String {
        return "Ticket(name='$name', notes='$notes', canMoveOn=$canMoveOn, phaseData=$phaseData, dateCreated=$dateCreated)"
    }
}