package com.digitalsamurai.graphlib.database.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {

    private var template = "dd.MM.yyyy HH:mm"
    @TypeConverter
    fun toLocalDateTime(value : String) : LocalDateTime =
        LocalDateTime.parse(value,
            DateTimeFormatter.ofPattern(template))

    @TypeConverter
    fun fromLocalDateTime(value : LocalDateTime) : String {
        return value.format(DateTimeFormatter.ofPattern(template))
    }


}