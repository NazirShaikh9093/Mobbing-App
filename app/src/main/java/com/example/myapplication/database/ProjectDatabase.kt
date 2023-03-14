package com.example.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.database.converters.StringListConverter
import com.example.myapplication.database.converters.TicketConverter
import com.example.myapplication.database.converters.TimeCreatedConverter
import com.example.myapplication.database.daos.ProjectDao
import com.example.myapplication.database.models.Project

@Database(
    entities = [Project::class],
    version = 2
)

@TypeConverters(TicketConverter::class, TimeCreatedConverter::class, StringListConverter::class)
abstract class ProjectDatabase: RoomDatabase() {

    abstract val dao: ProjectDao
}