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

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    appDatabase: AppDatabase,
    edgeToEdgePadding: PaddingValues,
    assetManager: AssetManager
    //preferencesManager: PreferencesManager,
    //adManager: GameAdManager?,
    //inAppPurchaseManager: InAppPurchaseManager?
) {
    NavHost(
        navController = navHostController,
        modifier = Modifier,
        startDestination = "initial_loading_scene"
    ) {

        composable(route = "initial_loading_scene") {
            InitialLoadingScene(
                viewModel = provideInitialLoadingViewModel(
                    appDatabase = appDatabase,
                    assetManager = assetManager
                )
            )
        }

        composable(route = "home_page_scene") {
            HomePageScene(viewModel = provideHomePageViewModel(appDatabase = appDatabase))
        }
    }
}