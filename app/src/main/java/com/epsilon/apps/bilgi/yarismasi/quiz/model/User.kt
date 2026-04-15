package com.epsilon.apps.bilgi.yarismasi.quiz.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false) val id: String = UUID.randomUUID().toString(),
    val username: String
)