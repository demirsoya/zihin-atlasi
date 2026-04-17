package com.epsilon.apps.bilgi.yarismasi.quiz.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.epsilon.apps.bilgi.yarismasi.quiz.model.UserProgress

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress LIMIT 1")
    suspend fun getUserProgress(): UserProgress?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserProgress(userProgress: UserProgress)

    @Query("UPDATE user_progress SET chapter = :chapter, episode = :episode, level = :level WHERE id = :id")
    suspend fun updateUserProgress(id: String, chapter: Int, episode: Int, level: Int)
}
