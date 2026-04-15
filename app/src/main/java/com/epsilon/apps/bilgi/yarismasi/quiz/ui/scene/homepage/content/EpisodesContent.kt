package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.homepage.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.epsilon.apps.bilgi.yarismasi.quiz.model.json.Episode

@Composable
fun EpisodesContent(episodes: List<Episode>) {
    LazyRow {
        items(episodes) { episode ->
            Image(
                painter = painterResource(episode.imageId),
                contentDescription = episode.episodeName
            )
        }
    }
}