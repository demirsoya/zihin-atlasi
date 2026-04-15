package com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource

@Composable
fun EpsilonImage(
    modifier: Modifier = Modifier,
    imageId: Int,
    contentDescription: String? = null,
    grayedOut: Boolean = false,

    ) {
    val matrix =
        remember { mutableStateOf(ColorMatrix().apply { setToSaturation(sat = 0F) }) }

    Image(
        modifier = modifier,
        painter = painterResource(id = imageId),
        contentDescription = contentDescription,
        colorFilter = if (grayedOut) ColorFilter.colorMatrix(matrix.value) else null
    )
}