package com.epsilon.apps.bilgi.yarismasi.quiz.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.epsilon.apps.bilgi.yarismasi.quiz.model.QuestionSet

@Dao
interface QuestionSetsDao {
    @Query("SELECT EXISTS(SELECT filename FROM question_sets WHERE filename = :filename)")
    suspend fun exists(filename: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuestionSet(questionSet: QuestionSet)
}
