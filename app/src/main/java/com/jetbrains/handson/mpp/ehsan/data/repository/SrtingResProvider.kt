package com.jetbrains.handson.mpp.ehsan.data.repository

import android.content.Context
import com.jetbrains.handson.mpp.ehsan.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringResProvider @Inject constructor(@ApplicationContext private val context:Context) {

    fun getStringFromSource(id:Int): String {
        return context.getString(id)
    }
}