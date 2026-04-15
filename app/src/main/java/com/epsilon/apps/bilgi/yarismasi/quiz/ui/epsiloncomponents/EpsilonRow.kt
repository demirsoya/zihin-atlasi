package com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledDp

@Composable
fun EpsilonVerticallyCenteredFullWidthRow(
    color: Int = R.color.app_trans,
    insidePadding: PaddingValues = PaddingValues(8.nonScaledDp),
    outsidePadding: PaddingValues = PaddingValues(0.nonScaledDp),
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(outsidePadding)
            .background(color = colorResource(id = color))
    ) {
        Row(
            modifier = Modifier.padding(insidePadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            content()
        }
    }
}
