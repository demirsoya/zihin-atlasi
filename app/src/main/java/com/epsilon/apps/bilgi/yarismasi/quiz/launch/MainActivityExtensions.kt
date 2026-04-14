package com.epsilon.apps.bilgi.yarismasi.quiz.launch

import androidx.room.Room
import com.epsilon.apps.bilgi.yarismasi.quiz.room.AppDatabase


fun MainActivity.getAppDatabase() =
    Room.databaseBuilder(
        context = this,
        klass = AppDatabase::class.java,
        name = "epsilon_quiz_zihin_atlasi_database"
    ).fallbackToDestructiveMigration(dropAllTables = true).build()

fun MainActivity.getAssetManager() = assets
