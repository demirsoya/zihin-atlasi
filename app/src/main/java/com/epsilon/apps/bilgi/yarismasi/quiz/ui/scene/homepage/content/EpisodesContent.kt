package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.homepage.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.model.ChapterEnum
import com.epsilon.apps.bilgi.yarismasi.quiz.model.UiEpisode
import com.epsilon.apps.bilgi.yarismasi.quiz.model.UserProgress
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonButton
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonCard
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonCenteredFullWidthColumn
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonImage
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonText
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledDp
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledSp

@Composable
fun EpisodesContent(
    chapter: ChapterEnum,
    episodes: List<UiEpisode>,
    userProgress: UserProgress,
    onActiveLevelClick: (episodeId: Int, level: Int) -> Unit
) {

    val clickedEpisode = remember { mutableStateOf<UiEpisode?>(null) }

    EpsilonText(
        modifier = Modifier.padding(top = 8.nonScaledDp, start = 12.nonScaledDp),
        text = "${chapter.id}. Bölüm: ${chapter.chapterName}",
        size = 14.nonScaledSp,
        textColor = R.color.app_main_text_color,
        fontWeight = FontWeight.Bold
    )

    LazyRow(contentPadding = PaddingValues(all = 8.nonScaledDp)) {
        items(episodes) { uiEpisode ->


            EpsilonCard(
                modifier = Modifier
                    .width(100.nonScaledDp)
                    .padding(horizontal = 4.nonScaledDp)
                    .clickable {
                        if (uiEpisode.isActive || uiEpisode.isCompleted) {
                            clickedEpisode.value = uiEpisode
                        }
                    }) {
                EpsilonCenteredFullWidthColumn(insidePadding = PaddingValues(all = 0.nonScaledDp)) {
                    EpsilonImage(
                        modifier = Modifier.fillMaxWidth(),
                        imageId = uiEpisode.episode.imageId,
                        grayedOut = !uiEpisode.isCompleted && !uiEpisode.isActive
                    )

                    EpsilonText(
                        modifier = Modifier.fillMaxWidth(),
                        text = uiEpisode.episode.episodeName,
                        size = 16.nonScaledSp,
                        textColor = R.color.app_main_text_color
                    )
                    Spacer(modifier = Modifier.padding(4.nonScaledDp))

                    EpsilonButton(
                        text = "Oyna",
                        modifier = Modifier.fillMaxWidth(0.9f),
                        textSize = 14.nonScaledSp,
                        iconId = if (!uiEpisode.isCompleted && !uiEpisode.isActive) R.drawable.lock else if (uiEpisode.isCompleted) R.drawable.completed else null
                    ) {
                        if (uiEpisode.isActive || uiEpisode.isCompleted) {
                            clickedEpisode.value = uiEpisode
                        }
                    }

                    Spacer(modifier = Modifier.padding(2.nonScaledDp))
                }
            }
        }
    }

    EpisodeDialog(
        uiEpisode = clickedEpisode.value,
        userProgress = userProgress,
        showDialog = clickedEpisode.value != null,
        onDismiss = {
            clickedEpisode.value = null
        },
        onActiveLevelClick = { episodeId, level ->
            //clickedEpisode.value = null
            onActiveLevelClick(episodeId, level)
        }
    )
}
