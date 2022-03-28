package com.example.bmimolotkov.database

import androidx.room.TypeConverter
import java.time.LocalDateTime

class DateConverter {

    @TypeConverter
    fun toLocalDateTime(stringDateTime: String) = LocalDateTime.parse(stringDateTime)

    @TypeConverter
    fun fromLocalDateTime(datetime: LocalDateTime) = datetime.toString()
}