package com.example.mobbingapp.database.converters

import androidx.room.TypeConverter
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class StringListConverter {

    @TypeConverter
    fun convertString(toConvert: List<String>?): String {
        return Gson().toJson(toConvert)
    }

    @TypeConverter
    fun convertFromJson(stringToConvert: String): List<String> {
        return try {
            val type = object : TypeToken<List<String>>() {}.type
            Gson().fromJson(stringToConvert, type)
        } catch(e:Exception) {
            println("Gson error")
            emptyList()
        }
    }
}