package com.example.mobbingapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mobbingapp.database.converters.StringListConverter
import com.example.mobbingapp.database.converters.TicketConverter
import com.example.mobbingapp.database.converters.TimeCreatedConverter
import com.example.mobbingapp.database.daos.ProjectDao
import com.example.mobbingapp.database.models.Project

@Database(
    entities = [Project::class],
    version = 3
)

@TypeConverters(TicketConverter::class, TimeCreatedConverter::class, StringListConverter::class)
abstract class ProjectDatabase: RoomDatabase() {

    abstract val dao: ProjectDao
}