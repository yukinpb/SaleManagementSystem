package com.example.fe.utilities

import com.google.gson.*
import java.lang.reflect.Type
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

class SqlDateSerializer : JsonSerializer<Date> {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun serialize(src: Date, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(dateFormat.format(src))
    }
}