package com.epsilon.apps.bilgi.yarismasi.quiz.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.epsilon.apps.bilgi.yarismasi.quiz.model.Question
import com.epsilon.apps.bilgi.yarismasi.quiz.model.QuestionSet
import com.epsilon.apps.bilgi.yarismasi.quiz.room.dao.QuestionSetsDao
import com.epsilon.apps.bilgi.yarismasi.quiz.room.dao.QuestionsDao

@TypeConverters(RoomConverters::class)
@Database(
    version = 2,
    entities = [Question::class, QuestionSet::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accessQuestions(): QuestionsDao
    abstract fun accessQuestionSets(): QuestionSetsDao
}
