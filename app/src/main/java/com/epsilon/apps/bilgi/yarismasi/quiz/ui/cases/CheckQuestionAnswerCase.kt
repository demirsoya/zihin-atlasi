package com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases

import com.epsilon.apps.bilgi.yarismasi.quiz.model.Question

class CheckQuestionAnswerCase {

    fun isCorrect(
        question: Question,
        selectedOptionKey: String
    ): Boolean {
        val normalizedSelected = selectedOptionKey.trim().uppercase()
        val normalizedCorrect = question.correctAnswer.trim().uppercase()

        if (normalizedSelected == normalizedCorrect) return true

        val correctOptionText = when (normalizedCorrect) {
            "A" -> question.optionA
            "B" -> question.optionB
            "C" -> question.optionC
            "D" -> question.optionD
            "E" -> question.optionE
            else -> null
        }

        return correctOptionText
            ?.trim()
            ?.equals(selectedOptionKey.trim(), ignoreCase = true)
            ?: false
    }
}
