package com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledDp

@Composable
fun EpsilonCard(
    modifier: Modifier = Modifier,
    color: Int = R.color.app_white,
    content: @Composable () -> Unit
) {

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(4.nonScaledDp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = color)
        )
    ) {
        content()
    }
}