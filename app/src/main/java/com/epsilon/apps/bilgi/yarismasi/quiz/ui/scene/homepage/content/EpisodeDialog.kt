package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.homepage.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.model.UiEpisode
import com.epsilon.apps.bilgi.yarismasi.quiz.model.UserProgress
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonCard
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonDialog
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonDialogContainer
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonImage
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonText
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledDp
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledSp

@Composable
fun EpisodeDialog(
    uiEpisode: UiEpisode?,
    userProgress: UserProgress,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    EpsilonDialog(
        showDialog = showDialog,
        canBeDismissed = true,
        size = 1f,
        onDismissRequest = {
            onDismiss()
        }) {

        uiEpisode?.let {
            val numberOfItemsInRow = 4
            val progressEpisodeId = userProgress.episode
            val progressLevel = userProgress.level.coerceIn(1, it.episode.numberOfLevels)
            val levelRows =
                (1..it.episode.numberOfLevels).toList().chunked(numberOfItemsInRow)

            EpsilonDialogContainer(title = it.episode.episodeName) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 8.nonScaledDp,
                            end = 8.nonScaledDp,
                            top = 8.nonScaledDp,
                            bottom = 0.nonScaledDp
                        ),
                    verticalArrangement = Arrangement.spacedBy(4.nonScaledDp)
                ) {
                    levelRows.forEach { rowLevels ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.nonScaledDp)
                        ) {
                            rowLevels.forEach { levelNumber ->
                                val isCompletedLevel =
                                    it.episode.id < progressEpisodeId ||
                                        (it.episode.id == progressEpisodeId && levelNumber < progressLevel)
                                val isCurrentLevel =
                                    it.episode.id == progressEpisodeId && levelNumber == progressLevel
                                val isLockedLevel = !isCompletedLevel && !isCurrentLevel

                                EpsilonCard(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .clickable(enabled = isCurrentLevel) { },
                                    color = if (isCompletedLevel) R.color.app_one else R.color.app_white
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isCompletedLevel || isLockedLevel) {
                                            EpsilonImage(
                                                modifier = Modifier
                                                    .align(Alignment.TopStart)
                                                    .padding(2.nonScaledDp)
                                                    .size(10.nonScaledDp),
                                                imageId = if (isCompletedLevel) R.drawable.completed else R.drawable.lock
                                            )
                                        }

                                        EpsilonText(
                                            text = levelNumber.toString(),
                                            textColor = R.color.app_main_text_color,
                                            size = 20.nonScaledSp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}