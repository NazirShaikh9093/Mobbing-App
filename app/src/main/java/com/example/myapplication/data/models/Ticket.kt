package com.example.myapplication.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Ticket(
    val name: String,
    val description: String,
    val canMoveOn: Boolean,
    val timeCreated: Calendar = Calendar.getInstance()
) : Parcelable {

    override fun toString(): String {
        return "Ticket(name='$name', description='$description', canMoveOn=$canMoveOn, timeCreated=$timeCreated)"
    }
}