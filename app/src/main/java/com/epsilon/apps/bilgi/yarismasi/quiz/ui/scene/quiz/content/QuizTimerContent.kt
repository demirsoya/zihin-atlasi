package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonImage
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonText
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledDp
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledSp

@Composable
fun QuizTimerContent(
    remainingSeconds: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.nonScaledDp) // İkon solda, metin sağda
    ) {
        EpsilonImage(
            modifier = Modifier.size(22.dp),
            imageId = R.drawable.time,
            tintColor = colorResource(id = R.color.app_white)
        )

        // Sabit dp vermeden genişliği korumanın en iyi yolu
        Box(contentAlignment = Alignment.CenterStart) {
            // 1. GÖRÜNMEZ YER TUTUCU:
            // Max süre 2 haneli ise "88", 3 haneli ise "888" yazabilirsin.
            // "8" fontlardaki en geniş rakam olduğu için en güvenli yer tutucudur.
            EpsilonText(
                modifier = Modifier.alpha(0f), // Yazıyı tamamen şeffaf/görünmez yapar
                text = "88",
                textColor = R.color.app_white,
                size = 16.nonScaledSp
            )

            // 2. GERÇEK SAYI: (09 yerine 9 görünür çünkü toString() doğrudan çevirir)
            EpsilonText(
                text = remainingSeconds.coerceAtLeast(0).toString(),
                textColor = R.color.app_white,
                size = 16.nonScaledSp
            )
        }
    }
}
