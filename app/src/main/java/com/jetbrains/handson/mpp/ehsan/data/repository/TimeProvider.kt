package com.jetbrains.handson.mpp.ehsan.data.repository

import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeProvider @Inject constructor(){
    fun getNowDate(): LocalDate{
        return LocalDate.now()
    }
}