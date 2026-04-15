package com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases

import com.epsilon.apps.bilgi.yarismasi.quiz.model.User
import com.epsilon.apps.bilgi.yarismasi.quiz.room.AppDatabase
import kotlin.random.Random

class UserCase(
    private val appDatabase: AppDatabase
) {

    suspend fun createUser() {
        val existingUser = appDatabase.accessUsers().getUser()
        if (existingUser != null) return

        val randomDigits = (1..7).joinToString(separator = "") {
            Random.nextInt(from = 0, until = 10).toString()
        }

        appDatabase.accessUsers().insertUser(
            User(username = "Misafir$randomDigits")
        )
    }

    suspend fun getUser(): User {
        return requireNotNull(appDatabase.accessUsers().getUser())
    }
}