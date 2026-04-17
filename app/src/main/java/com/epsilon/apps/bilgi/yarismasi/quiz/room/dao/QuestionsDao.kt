package com.epsilon.apps.bilgi.yarismasi.quiz.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.epsilon.apps.bilgi.yarismasi.quiz.model.Question

@Dao
interface QuestionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<Question>)

    @Query("SELECT * FROM questions WHERE usedBefore = 0")
    suspend fun getUnusedQuestions(): List<Question>

    @Query("UPDATE questions SET usedBefore = 1 WHERE id IN (:questionIds)")
    suspend fun markQuestionsAsUsed(questionIds: List<String>)

    @Query("UPDATE questions SET usedBefore = 0")
    suspend fun resetAllQuestionsAsUnused()
}