package com.epsilon.apps.bilgi.yarismasi.quiz.room

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class RoomConverters {
    private val moshi: Moshi = Moshi.Builder().build()

    @TypeConverter
    fun stringListToJson(value: List<String>): String {
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        return moshi.adapter<List<String>>(type).toJson(value)
    }

    @TypeConverter
    fun jsonToStringList(value: String): List<String> {
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        return moshi.adapter<List<String>>(type).fromJson(value).orEmpty()
    }
}
