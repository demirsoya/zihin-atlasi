package com.epsilon.apps.bilgi.yarismasi.quiz.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.epsilon.apps.bilgi.yarismasi.quiz.model.Question
import com.epsilon.apps.bilgi.yarismasi.quiz.model.QuestionSet
import com.epsilon.apps.bilgi.yarismasi.quiz.model.User
import com.epsilon.apps.bilgi.yarismasi.quiz.model.UserProgress
import com.epsilon.apps.bilgi.yarismasi.quiz.room.dao.QuestionSetsDao
import com.epsilon.apps.bilgi.yarismasi.quiz.room.dao.QuestionsDao
import com.epsilon.apps.bilgi.yarismasi.quiz.room.dao.UserDao
import com.epsilon.apps.bilgi.yarismasi.quiz.room.dao.UserProgressDao

@TypeConverters(RoomConverters::class)
@Database(
    version = 3,
    entities = [Question::class, QuestionSet::class, User::class, UserProgress::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accessQuestions(): QuestionsDao
    abstract fun accessQuestionSets(): QuestionSetsDao

    abstract fun accessUsers(): UserDao
    abstract fun accessUserProgress(): UserProgressDao
}
