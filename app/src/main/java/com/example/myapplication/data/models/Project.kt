package com.example.myapplication.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity
@Parcelize
data class Project(
    @PrimaryKey val id: Int,
    val name: String,
    val coverImage: String,
    val tickets: List<Ticket>,
    val timeCreated: Calendar = Calendar.getInstance()
): Parcelable {

    override fun toString(): String {
        return "Project(id=$id, name='$name', coverImage='$coverImage', tickets=$tickets, timeCreated=$timeCreated)"
    }
}
