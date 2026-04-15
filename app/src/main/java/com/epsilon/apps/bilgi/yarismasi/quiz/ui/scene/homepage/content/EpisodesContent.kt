package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.homepage.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.epsilon.apps.bilgi.yarismasi.quiz.model.Episode
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonCard
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledDp

@Composable
fun EpisodesContent(episodes: List<Episode>) {
    LazyRow(contentPadding = PaddingValues(all = 8.nonScaledDp)) {
        items(episodes) { episode ->

            EpsilonCard(modifier = Modifier.padding(horizontal = 4.nonScaledDp)) {
                Image(
                    painter = painterResource(episode.imageId),
                    contentDescription = episode.episodeName
                )
            }
        }
    }
}