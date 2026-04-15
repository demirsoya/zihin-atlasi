package com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases

import com.epsilon.apps.bilgi.yarismasi.quiz.model.Chapter
import com.epsilon.apps.bilgi.yarismasi.quiz.model.Episode

class EpisodeCase {

    fun getEpisodes(chapter: Chapter): List<Episode> {
        return Episode.entries
            .filter { it.chapter == chapter }
            .sortedBy { it.id }
    }
}