package com.example.mobbingapp.util

import java.util.*

object DateStuff {

    fun getDateCreated(date: Calendar): String {
        val day = date.get(Calendar.DAY_OF_MONTH)
        val dayWithOrdinal = dateOrdinals[day]
        val month = months[date.get(Calendar.MONTH)]
        val year = date.get(Calendar.YEAR)
        val hour = date.get(Calendar.HOUR)
        val minuteNum = date.get(Calendar.MINUTE)
        val minute = if (minuteNum < 10) "0$minuteNum" else minuteNum
        val amOrpM = ordinal[date.get(Calendar.AM_PM)]

        return "$dayWithOrdinal $month $year $hour:$minute$amOrpM"
    }


    fun compareDateToCurrent(dateToCompare: Calendar): String {
        val diff: Long = Calendar.getInstance().timeInMillis - dateToCompare.timeInMillis
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days / 7
        val months = weeks / 4

        var timeString = ""
        if (months > 0) timeString += "${months}mo "
        if (days > 0) timeString += "${days}d "
        if (hours > 0) timeString += "${hours}hr "
        if (minutes > 0) timeString += "${minutes}m "
        if (seconds > 0) timeString += "${seconds}s"

        return timeString
    }

    private val dateOrdinals = arrayOf(
        "0th", "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th",
        "10th", "11th", "12th", "13th", "14th", "15th", "16th", "17th", "18th", "19th",
        "20th", "21st", "22nd", "23rd", "24th", "25th", "26th", "27th", "28th", "29th",
        "30th", "31st"
    )

    private val months = arrayOf(
        "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
    )

    private val ordinal = arrayOf("am", "pm")
}