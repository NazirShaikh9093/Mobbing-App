package com.example.myapplication.database.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity
@Parcelize
data class Project(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val imageUUID: String,
    val tickets: List<Ticket>,
    val teamMembers: List<String>,
    val timeCreated: Calendar = Calendar.getInstance()
): Parcelable {

    override fun toString(): String {
        return "Project(id=$id, name='$name', imageUUID='$imageUUID', tickets=$tickets, teamMembers=$teamMembers, timeCreated=$timeCreated)"
    }
}
