package com.example.mobbingapp.database.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class PhaseData(
    val currentPhase: TicketPhase = TicketPhase.EMPATHISE,
    val empathiseCompletedTime: Calendar? = null,
    val timeInEmpathise: String? = null,
    val defineCompletedTime: Calendar? = null,
    val timeInDefine: String? = null,
    val ideateCompletedTime: Calendar? = null,
    val timeInIdeate: String? = null,
    val prototypeCompletedTime: Calendar? = null,
    val timeInPrototype: String? = null,
    val testCompletedTime: Calendar? = null,
    val timeInTest: String? = null
): Parcelable {

    override fun toString(): String {
        return "PhaseData(currentPhase=$currentPhase, empathiseCompletedTime=$empathiseCompletedTime, timeInEmpathise='$timeInEmpathise', defineCompletedTime=$defineCompletedTime, timeInDefine='$timeInDefine', ideateCompletedTime=$ideateCompletedTime, timeInIdeate='$timeInIdeate', prototypeCompletedTime=$prototypeCompletedTime, timeInPrototype='$timeInPrototype', testCompletedTime=$testCompletedTime, timeInTest='$timeInTest')"
    }
}