package com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledSp
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.theme.fonts

@Composable
fun EpsilonText(
    modifier: Modifier = Modifier,
    text: String,
    size: TextUnit = 20.nonScaledSp,
    textColor: Int = R.color.app_black,
    textAlign: TextAlign = TextAlign.Center,
    fontWeight: FontWeight = FontWeight.Normal,
    lineHeight: TextUnit = size,
    textDecoration: TextDecoration = TextDecoration.None,
    fontFamily: androidx.compose.ui.text.font.FontFamily = fonts
) {
    Text(
        fontFamily = fontFamily,
        fontSize = size,
        fontWeight = fontWeight,
        text = text,
        color = colorResource(id = textColor),
        textAlign = textAlign,
        modifier = modifier,
        lineHeight = lineHeight,
        textDecoration = textDecoration
    )
}
