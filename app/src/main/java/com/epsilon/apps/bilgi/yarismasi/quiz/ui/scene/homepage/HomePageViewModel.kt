package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.homepage

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epsilon.apps.bilgi.yarismasi.quiz.room.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Suppress("UNCHECKED_CAST")
@Composable
fun provideHomePageViewModel(
    appDatabase: AppDatabase,
): HomePageViewModel {
    return viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = HomePageViewModel(
            appDatabase = appDatabase
        ) as T
    })
}

class HomePageViewModel(
    private val appDatabase: AppDatabase
) : ViewModel() {

    sealed class HomePageUiState {
        data class Loaded(
            val a: Int
        ) : HomePageUiState()

        data object Loading : HomePageUiState()
        data object Error : HomePageUiState()
    }

    private val mUiState = MutableStateFlow<HomePageUiState>(HomePageUiState.Loading)
    val homePageUiState: StateFlow<HomePageUiState> = mUiState.asStateFlow()

}