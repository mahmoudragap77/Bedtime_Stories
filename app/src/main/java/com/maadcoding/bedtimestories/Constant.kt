package com.maadcoding.bedtimestories

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore("settings")

object Constant{
        val NAME = "name"
        val DETAILS = "details"
        val PICTURE = "picture"
        val SOUND = "sound"
}

object Keys{
        val SIZE_KEY = intPreferencesKey("size")
}