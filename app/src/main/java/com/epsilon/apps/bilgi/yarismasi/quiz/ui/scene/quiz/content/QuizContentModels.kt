package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.content

data class QuizContentOptionUi(
    val key: String,
    val text: String,
    val isClickable: Boolean,
    val indicatorColor: Int,
    val textColor: Int
)

data class QuizContentAnswerResult(
    val isCorrect: Boolean
)
