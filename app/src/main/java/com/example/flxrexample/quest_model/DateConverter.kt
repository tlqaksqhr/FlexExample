package com.example.flxrexample.quest_model

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun toDate(dateLong : Long) : Date? {

        if(dateLong != null)
            return Date(dateLong)

        return null
    }

    @TypeConverter
    fun fromDate(date: Date) : Long? {

        if(date != null)
            return date.time

        return null
    }
}