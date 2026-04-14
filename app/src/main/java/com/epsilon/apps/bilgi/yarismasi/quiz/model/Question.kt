package com.epsilon.apps.bilgi.yarismasi.quiz.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = false) val id: String = UUID.randomUUID().toString(),
    val usedBefore: Boolean = false,
    val questionText: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val optionE: String,
    val correctAnswer: String,
    val difficulty: Int,
    val categoryId: Int,
    val categoryName: String,
    val infoNote: String,
    val hashtags: List<String>
)