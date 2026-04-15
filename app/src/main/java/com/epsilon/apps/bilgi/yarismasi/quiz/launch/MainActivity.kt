package com.epsilon.apps.bilgi.yarismasi.quiz.launch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.epsilon.apps.bilgi.yarismasi.quiz.navigation.NavigationGraph
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.theme.ZihinAtlasiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        applyFullScreenApp()

        val appDatabase = getAppDatabase()
        val assetManager = getAssetManager()

        setContent {
            ZihinAtlasiTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavigationGraph(
                        navHostController = navController,
                        appDatabase = appDatabase,
                        edgeToEdgePadding = innerPadding,
                        assetManager = assetManager
                    )
                }
            }
        }
    }
}