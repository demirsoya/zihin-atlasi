package com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases

import com.epsilon.apps.bilgi.yarismasi.quiz.model.EpisodeEnum
import com.epsilon.apps.bilgi.yarismasi.quiz.model.UserProgress
import com.epsilon.apps.bilgi.yarismasi.quiz.room.AppDatabase

class UserProgressCase(
    private val appDatabase: AppDatabase
) {

    suspend fun createUserProgress() {
        val existingProgress = appDatabase.accessUserProgress().getUserProgress()
        if (existingProgress != null) return

        appDatabase.accessUserProgress().insertUserProgress(
            UserProgress(chapter = 1, episode = 1, level = 1)
        )
    }

    suspend fun getUserProgress(): UserProgress {
        return requireNotNull(appDatabase.accessUserProgress().getUserProgress())
    }

    suspend fun advanceToNextLevel() {
        val currentProgress = getUserProgress()
        val currentEpisode = EpisodeEnum.entries.firstOrNull { it.id == currentProgress.episode }
            ?: return

        val nextProgress = if (currentProgress.level < currentEpisode.numberOfLevels) {
            currentProgress.copy(level = currentProgress.level + 1)
        } else {
            val nextEpisode = EpisodeEnum.entries.firstOrNull { it.id == currentProgress.episode + 1 }
            if (nextEpisode != null) {
                currentProgress.copy(
                    episode = nextEpisode.id,
                    level = 1
                )
            } else {
                currentProgress
            }
        }

        appDatabase.accessUserProgress().updateUserProgress(
            id = currentProgress.id,
            chapter = nextProgress.chapter,
            episode = nextProgress.episode,
            level = nextProgress.level
        )
    }
}