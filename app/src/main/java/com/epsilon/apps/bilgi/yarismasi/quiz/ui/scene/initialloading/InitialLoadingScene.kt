package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.initialloading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonText
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledDp
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledSp

@Composable
fun InitialLoadingScene(
    viewModel: InitialLoadingViewModel,
    edgeToEdgePadding: PaddingValues,
    initialLoadCompleted: () -> Unit
) {
    val uiState = viewModel.initialLoadingUiState.collectAsStateWithLifecycle().value

    LaunchedEffect(uiState) {
        if (uiState is InitialLoadingViewModel.InitialLoadingUiState.Loaded) {
            initialLoadCompleted()
        }
    }

    Box(
        modifier = Modifier
            .padding(edgeToEdgePadding)
            .fillMaxSize()
            .background(color = colorResource(id = R.color.app_white))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.nonScaledDp, vertical = 32.nonScaledDp)
                .align(Alignment.BottomCenter)
        ) {

            EpsilonText(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Yarışma Başlatılıyor",
                fontWeight = FontWeight.Bold,
                textColor = R.color.app_main_text_color,
                size = 22.nonScaledSp
            )

            Spacer(modifier = Modifier.height(4.nonScaledDp))

            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.nonScaledDp),
                color = colorResource(R.color.app_one),
                trackColor = colorResource(R.color.app_positive_indicator)
            )
        }
    }
}