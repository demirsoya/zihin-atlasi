package com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledDp
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledSp
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.theme.fonts

enum class EpsilonButtonIconPosition {
    Start,
    End
}

@Composable
fun EpsilonButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Int = R.color.app_three,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = 8.nonScaledDp,
        vertical = 4.nonScaledDp
    ),
    iconId: Int? = null,
    iconPosition: EpsilonButtonIconPosition = EpsilonButtonIconPosition.Start,
    iconContentDescription: String? = null,
    iconSize: Dp = 20.dp,
    iconTint: Int? = null,
    iconTextSpacing: Dp = 8.nonScaledDp,
    textSize: TextUnit = 20.nonScaledSp,
    textColor: Int = R.color.app_white,
    textAlign: TextAlign = TextAlign.Center,
    fontWeight: FontWeight = FontWeight.Normal,
    lineHeight: TextUnit = textSize,
    textDecoration: TextDecoration = TextDecoration.None,
    fontFamily: FontFamily = fonts,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.nonScaledDp))
            .background(color = colorResource(id = backgroundColor))
            .clickable(enabled = enabled, onClick = onClick)
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val icon: (@Composable () -> Unit)? = iconId?.let {
                {
                    Image(
                        modifier = Modifier.size(iconSize),
                        painter = painterResource(id = it),
                        contentDescription = iconContentDescription,
                        colorFilter = iconTint?.let { tint ->
                            ColorFilter.tint(color = colorResource(id = tint))
                        }
                    )
                }
            }

            if (iconPosition == EpsilonButtonIconPosition.Start) {
                icon?.invoke()
                if (icon != null) {
                    Spacer(modifier = Modifier.width(iconTextSpacing))
                }
            }

            EpsilonText(
                text = text,
                size = textSize,
                textColor = textColor,
                textAlign = textAlign,
                fontWeight = fontWeight,
                lineHeight = lineHeight,
                textDecoration = textDecoration,
                fontFamily = fontFamily
            )

            if (iconPosition == EpsilonButtonIconPosition.End) {
                if (icon != null) {
                    Spacer(modifier = Modifier.width(iconTextSpacing))
                }
                icon?.invoke()
            }
        }
    }
}
