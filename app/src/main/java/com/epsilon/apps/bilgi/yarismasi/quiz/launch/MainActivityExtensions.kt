package com.epsilon.apps.bilgi.yarismasi.quiz.launch

import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.room.Room
import com.epsilon.apps.bilgi.yarismasi.quiz.room.AppDatabase


fun MainActivity.getAppDatabase() =
    Room.databaseBuilder(
        context = this,
        klass = AppDatabase::class.java,
        name = "epsilon_quiz_zihin_atlasi_database"
    ).fallbackToDestructiveMigration(dropAllTables = true).build()

fun MainActivity.getAssetManager() = assets

fun MainActivity.applyFullScreenApp() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(window, window.decorView).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}
