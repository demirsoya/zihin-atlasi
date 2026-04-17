package com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases

import androidx.room.withTransaction
import com.epsilon.apps.bilgi.yarismasi.quiz.model.ActiveQuizQuestion
import com.epsilon.apps.bilgi.yarismasi.quiz.model.Question
import com.epsilon.apps.bilgi.yarismasi.quiz.room.AppDatabase
import kotlin.random.Random

class QuizQuestionCase(
    private val appDatabase: AppDatabase
) {

    suspend fun prepareAndGetActiveQuestions(): List<ActiveQuizQuestion> {
        return appDatabase.withTransaction {
            val activeDao = appDatabase.accessActiveQuizQuestions()
            val questionsDao = appDatabase.accessQuestions()

            if (activeDao.getCount() == 0) {
                val unusedQuestions = questionsDao.getUnusedQuestions()
                val selected = selectQuestions(unusedQuestions)
                if (selected.isNotEmpty()) {
                    val sortedByDifficulty = selected.sortedWith(compareBy<Question> { it.difficulty }.thenBy { it.id })
                    val activeQuestions = sortedByDifficulty.map { it.toActiveQuizQuestion() }
                    activeDao.insertQuestions(activeQuestions)
                    questionsDao.markQuestionsAsUsed(sortedByDifficulty.map { it.id })
                }
            }

            activeDao.getQuestionsOrdered()
        }
    }

    private fun selectQuestions(unusedQuestions: List<Question>): List<Question> {
        if (unusedQuestions.isEmpty()) return emptyList()

        val categoryTargets = (1..6).associateWith { 2 }.toMutableMap()
        val difficultyTargets = mutableMapOf(1 to 4, 2 to 4, 3 to 2, 4 to 1, 5 to 1)

        val available = unusedQuestions.shuffled(Random(System.currentTimeMillis())).toMutableList()
        val selected = mutableListOf<Question>()

        fun remainingCategory(cat: Int): Int = categoryTargets[cat] ?: 0
        fun remainingDifficulty(diff: Int): Int = difficultyTargets[diff] ?: 0

        fun pickAndConsume(predicate: (Question) -> Boolean): Question? {
            val picked = available.firstOrNull(predicate) ?: return null
            available.remove(picked)
            selected.add(picked)

            if (remainingCategory(picked.categoryId) > 0) {
                categoryTargets[picked.categoryId] = remainingCategory(picked.categoryId) - 1
            }
            if (remainingDifficulty(picked.difficulty) > 0) {
                difficultyTargets[picked.difficulty] = remainingDifficulty(picked.difficulty) - 1
            }
            return picked
        }

        while (selected.size < 12) {
            pickAndConsume {
                remainingCategory(it.categoryId) > 0 && remainingDifficulty(it.difficulty) > 0
            } ?: break
        }

        for (categoryId in 1..6) {
            while (selected.size < 12 && remainingCategory(categoryId) > 0) {
                val preferred = pickAndConsume {
                    it.categoryId == categoryId && remainingDifficulty(it.difficulty) > 0
                }
                if (preferred != null) continue

                val fallback = pickAndConsume { it.categoryId == categoryId }
                if (fallback == null) {
                    categoryTargets[categoryId] = 0
                    break
                }
            }
        }

        for (difficulty in 1..5) {
            while (selected.size < 12 && remainingDifficulty(difficulty) > 0) {
                val preferred = pickAndConsume {
                    it.difficulty == difficulty && remainingCategory(it.categoryId) > 0
                }
                if (preferred != null) continue

                val fallback = pickAndConsume { it.difficulty == difficulty }
                if (fallback == null) {
                    difficultyTargets[difficulty] = 0
                    break
                }
            }
        }

        while (selected.size < 12 && available.isNotEmpty()) {
            pickAndConsume { true }
        }

        return selected.take(12)
    }

    private fun Question.toActiveQuizQuestion(): ActiveQuizQuestion {
        return ActiveQuizQuestion(
            id = id,
            usedBefore = true,
            questionText = questionText,
            optionA = optionA,
            optionB = optionB,
            optionC = optionC,
            optionD = optionD,
            optionE = optionE,
            correctAnswer = correctAnswer,
            difficulty = difficulty,
            categoryId = categoryId,
            categoryName = categoryName,
            infoNote = infoNote,
            hashtags = hashtags
        )
    }
}
