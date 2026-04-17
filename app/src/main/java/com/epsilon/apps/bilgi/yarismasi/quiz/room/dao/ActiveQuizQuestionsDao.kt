package com.epsilon.apps.bilgi.yarismasi.quiz.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.epsilon.apps.bilgi.yarismasi.quiz.model.ActiveQuizQuestion

@Dao
interface ActiveQuizQuestionsDao {

    @Query("SELECT COUNT(*) FROM active_quiz_questions")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<ActiveQuizQuestion>)

    @Query("SELECT * FROM active_quiz_questions ORDER BY difficulty ASC, id ASC")
    suspend fun getQuestionsOrdered(): List<ActiveQuizQuestion>

    @Query("UPDATE active_quiz_questions SET usedBefore = 1 WHERE id = :questionId")
    suspend fun markQuestionAsUsed(questionId: String)

    @Query("DELETE FROM active_quiz_questions")
    suspend fun clear()
}
