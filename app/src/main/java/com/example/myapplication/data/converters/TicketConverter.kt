package com.example.myapplication.data.converters

import androidx.room.TypeConverter
import com.example.myapplication.data.models.Ticket
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class TicketConverter {

    @TypeConverter
    fun convertString(toConvert: List<Ticket>?): String {
        return Gson().toJson(toConvert)
    }

    @TypeConverter
    fun convertFromJson(stringToConvert: String): List<Ticket> {
        return try {
            val type = object : TypeToken<List<Ticket>>() {}.type
            Gson().fromJson(stringToConvert, type)
        } catch(e:Exception) {
            println("Gson error")
            emptyList()
        }
    }
}