package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.homepage.content

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.model.User
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonImage
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonText
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonVerticallyCenteredFullWidthColumn
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonVerticallyCenteredFullWidthRow
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledDp
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledSp

@Composable
fun UserContent(user: User) {
    EpsilonVerticallyCenteredFullWidthRow(color = R.color.app_one) {
        EpsilonImage(modifier = Modifier.size(size = 64.dp), imageId = R.drawable.avatar_one)
        EpsilonVerticallyCenteredFullWidthColumn(insidePadding = PaddingValues(all = 4.nonScaledDp)) {
            EpsilonText(text = user.username, size = 16.nonScaledSp)
            //TODO: Add user level
            EpsilonText(text = "Uzman", size = 14.nonScaledSp)
        }
    }
}