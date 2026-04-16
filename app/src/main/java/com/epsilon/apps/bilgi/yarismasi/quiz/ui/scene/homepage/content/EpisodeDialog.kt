package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.homepage.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.model.UiEpisode
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonCard
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonDialog
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonDialogContainer
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonText
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledDp
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledSp

@Composable
fun EpisodeDialog(
    uiEpisode: UiEpisode?,
    title: String,
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
            val levelRows =
                (1..uiEpisode.episode.numberOfLevels).toList().chunked(numberOfItemsInRow)

            EpsilonDialogContainer(title = title) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.nonScaledDp),
                    verticalArrangement = Arrangement.spacedBy(8.nonScaledDp)
                ) {
                    levelRows.forEach { rowLevels ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.nonScaledDp)
                        ) {
                            rowLevels.forEach { levelNumber ->
                                EpsilonCard(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        EpsilonText(
                                            text = levelNumber.toString(),
                                            textColor = R.color.app_main_text_color,
                                            size = 16.nonScaledSp
                                        )
                                    }
                                }
                            }

                            repeat(numberOfItemsInRow - rowLevels.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}