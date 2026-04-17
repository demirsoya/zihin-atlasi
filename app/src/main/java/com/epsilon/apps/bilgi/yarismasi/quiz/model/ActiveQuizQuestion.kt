package com.epsilon.apps.bilgi.yarismasi.quiz.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "active_quiz_questions")
data class ActiveQuizQuestion(
    @PrimaryKey(autoGenerate = false) val id: String,
    val usedBefore: Boolean = true,
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
