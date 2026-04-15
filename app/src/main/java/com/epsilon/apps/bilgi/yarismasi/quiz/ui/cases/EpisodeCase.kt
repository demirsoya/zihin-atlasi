package com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases

import com.epsilon.apps.bilgi.yarismasi.quiz.model.ChapterEnum
import com.epsilon.apps.bilgi.yarismasi.quiz.model.EpisodeEnum
import com.epsilon.apps.bilgi.yarismasi.quiz.model.UiEpisode
import com.epsilon.apps.bilgi.yarismasi.quiz.model.UserProgress

class EpisodeCase {

    fun getEpisodes(chapter: ChapterEnum): List<UiEpisode> {
        return EpisodeEnum.entries
            .filter { it.chapter == chapter }
            .sortedBy { it.id }
            .map { UiEpisode(episode = it) }
    }

    fun applyUserProgress(
        episodes: List<UiEpisode>,
        userProgress: UserProgress
    ): List<UiEpisode> {
        val activeEpisodeId = userProgress.episode

        return episodes.map { uiEpisode ->
            val episodeId = uiEpisode.episode.id
            val isCompleted = episodeId < activeEpisodeId
            val isActive = episodeId == activeEpisodeId
            uiEpisode.copy(isCompleted = isCompleted, isActive = isActive)
        }
    }
}