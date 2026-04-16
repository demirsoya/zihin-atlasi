package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.homepage.content

import androidx.compose.runtime.Composable
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonDialog
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonDialogContainer
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonText

@Composable
fun EpisodeDialog(title: String, showDialog: Boolean, onDismiss: () -> Unit) {

    EpsilonDialog(
        showDialog = showDialog,
        canBeDismissed = true,
        size = 1f,
        onDismissRequest = {
            onDismiss()
        }) {
        EpsilonDialogContainer(title = title) {
            EpsilonText(text = " djidjs")
        }
    }
}