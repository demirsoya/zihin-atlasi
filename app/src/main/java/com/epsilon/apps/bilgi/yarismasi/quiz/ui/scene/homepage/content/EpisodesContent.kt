package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.homepage.content

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.epsilon.apps.bilgi.yarismasi.quiz.model.UiEpisode
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonCard
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonImage
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledDp

@Composable
fun EpisodesContent(episodes: List<UiEpisode>) {
    LazyRow(contentPadding = PaddingValues(all = 8.nonScaledDp)) {
        items(episodes) { uiEpisode ->

            EpsilonCard(modifier = Modifier.padding(horizontal = 4.nonScaledDp)) {
                EpsilonImage(
                    imageId = uiEpisode.episode.imageId,
                    grayedOut = !uiEpisode.isCompleted && !uiEpisode.isActive
                )
            }
        }
    }
}