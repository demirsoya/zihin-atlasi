package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.epsilon.apps.bilgi.yarismasi.quiz.R

@Composable
fun HomePageScene(viewModel: HomePageViewModel, edgeToEdgePadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(edgeToEdgePadding)
            .fillMaxSize()
            .background(color = colorResource(id = R.color.app_two))
    ) {

    }
}