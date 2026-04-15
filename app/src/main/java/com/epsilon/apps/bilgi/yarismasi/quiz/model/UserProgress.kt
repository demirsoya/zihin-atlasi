package com.epsilon.apps.bilgi.yarismasi.quiz.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey(autoGenerate = false) val id: String = UUID.randomUUID().toString(),
    val chapter: Int,
    val episode: Int,
    val level: Int
)