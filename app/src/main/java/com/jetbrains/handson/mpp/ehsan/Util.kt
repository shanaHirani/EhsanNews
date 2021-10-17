package com.jetbrains.handson.mpp.ehsan

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.netFormat():String{
    return this.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
}

fun LocalDate.uiFormat():String{
    return this.format(DateTimeFormatter.ofPattern("dd MMM uuuu"))
}