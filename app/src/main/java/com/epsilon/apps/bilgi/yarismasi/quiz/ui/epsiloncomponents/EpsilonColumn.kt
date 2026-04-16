package com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledDp

@Composable
fun EpsilonVerticallyCenteredFullWidthColumn(
    color: Int = R.color.app_trans,
    outsidePadding: PaddingValues = PaddingValues(0.nonScaledDp),
    insidePadding: PaddingValues = PaddingValues(8.nonScaledDp),
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(outsidePadding)
            .background(color = colorResource(id = color))
    ) {
        Column(
            modifier = Modifier.padding(insidePadding),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            content()
        }
    }
}


@Composable
fun EpsilonCenteredFullWidthColumn(
    color: Int = R.color.app_trans,
    outsidePadding: PaddingValues = PaddingValues(0.nonScaledDp),
    insidePadding: PaddingValues = PaddingValues(8.nonScaledDp),
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(outsidePadding)
            .background(color = colorResource(id = color))
    ) {
        Column(
            modifier = Modifier.padding(insidePadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            content()
        }
    }
}
