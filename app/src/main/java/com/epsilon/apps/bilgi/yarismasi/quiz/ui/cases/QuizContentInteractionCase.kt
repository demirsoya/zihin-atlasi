package com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases

import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.model.Question
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.content.QuizContentOptionUi

class QuizContentInteractionCase {

    fun buildNeutralOptions(question: Question): List<QuizContentOptionUi> {
        return listOf(
            QuizContentOptionUi(
                key = "A",
                text = question.optionA,
                isClickable = true,
                indicatorColor = R.color.app_white,
                textColor = R.color.app_main_text_color
            ),
            QuizContentOptionUi(
                key = "B",
                text = question.optionB,
                isClickable = true,
                indicatorColor = R.color.app_white,
                textColor = R.color.app_main_text_color
            ),
            QuizContentOptionUi(
                key = "C",
                text = question.optionC,
                isClickable = true,
                indicatorColor = R.color.app_white,
                textColor = R.color.app_main_text_color
            ),
            QuizContentOptionUi(
                key = "D",
                text = question.optionD,
                isClickable = true,
                indicatorColor = R.color.app_white,
                textColor = R.color.app_main_text_color
            ),
            QuizContentOptionUi(
                key = "E",
                text = question.optionE,
                isClickable = true,
                indicatorColor = R.color.app_white,
                textColor = R.color.app_main_text_color
            )
        )
    }

    fun buildAnsweredOptions(
        optionItems: List<QuizContentOptionUi>,
        selectedOptionKey: String,
        isCorrect: Boolean
    ): List<QuizContentOptionUi> {
        val selectedColor = if (isCorrect) R.color.app_positive_indicator else R.color.app_negative_indicator
        return optionItems.map { option ->
            if (option.key == selectedOptionKey) {
                option.copy(
                    isClickable = false,
                    indicatorColor = selectedColor,
                    textColor = R.color.app_white
                )
            } else {
                option.copy(isClickable = false)
            }
        }
    }
}
