package com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases

import android.content.res.AssetManager
import androidx.room.withTransaction
import com.epsilon.apps.bilgi.yarismasi.quiz.model.Question
import com.epsilon.apps.bilgi.yarismasi.quiz.model.QuestionSet
import com.epsilon.apps.bilgi.yarismasi.quiz.model.json.QuestionJson
import com.epsilon.apps.bilgi.yarismasi.quiz.room.AppDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Loads questions from the raw folder into the database.
 */
class RawQuestionsCase(
    private val assetManager: AssetManager,
    private val appDatabase: AppDatabase
) {

    suspend fun loadNewQuestionsToDatabase() {
        withContext(Dispatchers.IO) {
            val rawFiles = assetManager.list("raw").orEmpty()
            val questionSetFiles = rawFiles
                .filter { it.startsWith("question_sets_") && it.endsWith(".json") }
                .sorted()

            val moshi = Moshi.Builder().build()
            val listType = Types.newParameterizedType(List::class.java, QuestionJson::class.java)
            val adapter = moshi.adapter<List<QuestionJson>>(listType)

            for (filename in questionSetFiles) {
                val alreadyImported = appDatabase.accessQuestionSets().exists(filename)
                if (alreadyImported) continue

                runCatching {
                    val json = assetManager.open("raw/$filename").bufferedReader().use { it.readText() }
                    val parsed = adapter.fromJson(json).orEmpty()

                    appDatabase.withTransaction {
                        parsed.chunked(300).forEach { chunk ->
                            val questions = chunk.map { q ->
                                Question(
                                    questionText = q.questionText,
                                    optionA = q.options.a,
                                    optionB = q.options.b,
                                    optionC = q.options.c,
                                    optionD = q.options.d,
                                    optionE = q.options.e,
                                    correctAnswer = q.correctAnswer,
                                    difficulty = q.difficulty,
                                    categoryId = q.categoryId,
                                    categoryName = q.categoryName,
                                    infoNote = q.infoNote,
                                    hashtags = q.hashtags
                                )
                            }
                            appDatabase.accessQuestions().insertQuestions(questions)
                        }

                        appDatabase.accessQuestionSets()
                            .insertQuestionSet(QuestionSet(filename = filename))
                    }
                }.getOrElse { error ->
                    throw IllegalStateException("Failed while importing $filename", error)
                }
            }
        }
    }
}