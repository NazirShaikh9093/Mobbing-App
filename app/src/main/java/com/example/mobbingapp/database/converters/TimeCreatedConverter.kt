package com.example.mobbingapp.database.converters

import androidx.room.TypeConverter
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.util.*

class TimeCreatedConverter {

    @TypeConverter
    fun convertString(toConvert: Calendar?): String {
        return Gson().toJson(toConvert)
    }

    @TypeConverter
    fun convertFromJson(stringToConvert: String): Calendar {
        return try {
            val type = object : TypeToken<Calendar>() {}.type
            Gson().fromJson(stringToConvert, type)
        } catch(e:Exception) {
            println("Gson error")
            Calendar.getInstance()
        }
    }
}