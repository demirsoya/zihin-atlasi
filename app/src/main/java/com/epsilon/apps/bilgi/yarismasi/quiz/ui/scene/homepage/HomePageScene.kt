package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.homepage.content.EpisodesContent
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.homepage.content.UserContent

@Composable
fun HomePageScene(viewModel: HomePageViewModel, edgeToEdgePadding: PaddingValues) {

    val context = LocalContext.current
    val uiState = viewModel.homePageUiState.collectAsStateWithLifecycle().value

    LaunchedEffect(context) {
        viewModel.loadHomePage()
    }

    LazyColumn(
        modifier = Modifier
            .padding(edgeToEdgePadding)
            .fillMaxSize()
            .background(color = colorResource(id = R.color.app_white))
    ) {

        when (uiState) {
            HomePageViewModel.HomePageUiState.Error -> TODO()
            is HomePageViewModel.HomePageUiState.Loaded -> {
                item {
                    UserContent(user = uiState.user)
                }
                item {
                    EpisodesContent(episodes = uiState.episodes)
                }
            }

            HomePageViewModel.HomePageUiState.Loading -> {

            }
        }
    }
}