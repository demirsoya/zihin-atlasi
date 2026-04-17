package com.epsilon.apps.bilgi.yarismasi.quiz.navigation

import android.content.res.AssetManager
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.epsilon.apps.bilgi.yarismasi.quiz.room.AppDatabase
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.homepage.HomePageScene
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.homepage.provideHomePageViewModel
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.initialloading.InitialLoadingScene
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.initialloading.provideInitialLoadingViewModel
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.QuizScene
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.content.provideQuizContentViewModel
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.provideStoryModeQuizViewModel

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    appDatabase: AppDatabase,
    edgeToEdgePadding: PaddingValues,
    assetManager: AssetManager
) {
    NavHost(
        navController = navHostController,
        modifier = Modifier,
        startDestination = "initial_loading_scene"
    ) {

        composable(route = "initial_loading_scene") {
            InitialLoadingScene(
                edgeToEdgePadding = edgeToEdgePadding,
                viewModel = provideInitialLoadingViewModel(
                    appDatabase = appDatabase,
                    assetManager = assetManager
                ),
                initialLoadCompleted = {
                    navHostController.navigate("home_page_scene") {
                        popUpTo("initial_loading_scene") {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = "home_page_scene") {
            HomePageScene(
                edgeToEdgePadding = edgeToEdgePadding,
                viewModel = provideHomePageViewModel(appDatabase = appDatabase),
                onActiveLevelClick = { _, _ ->
                    navHostController.navigate("quiz_scene")
                }
            )
        }

        composable(route = "quiz_scene") {
            QuizScene(
                edgeToEdgePadding = edgeToEdgePadding,
                viewModel = provideStoryModeQuizViewModel(appDatabase = appDatabase),
                quizContentViewModel = provideQuizContentViewModel()
            )
        }
    }
}