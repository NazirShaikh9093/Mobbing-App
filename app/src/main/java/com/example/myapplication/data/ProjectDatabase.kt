package com.example.myapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.data.converters.TicketConverter
import com.example.myapplication.data.converters.TimeCreatedConverter
import com.example.myapplication.data.models.Project

@Database(
    entities = [Project::class],
    version = 1
)

@TypeConverters(TicketConverter::class, TimeCreatedConverter::class)
abstract class ProjectDatabase: RoomDatabase() {

}