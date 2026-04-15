package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.homepage

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epsilon.apps.bilgi.yarismasi.quiz.model.Chapter
import com.epsilon.apps.bilgi.yarismasi.quiz.model.json.Episode
import com.epsilon.apps.bilgi.yarismasi.quiz.room.AppDatabase
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases.EpisodeCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
@Composable
fun provideHomePageViewModel(
    appDatabase: AppDatabase,
): HomePageViewModel {
    return viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = HomePageViewModel(
            appDatabase = appDatabase,
            episodeCase = EpisodeCase()
        ) as T
    })
}

class HomePageViewModel(
    private val appDatabase: AppDatabase,
    private val episodeCase: EpisodeCase,
) : ViewModel() {

    sealed class HomePageUiState {
        data class Loaded(
            val episodes: List<Episode>
        ) : HomePageUiState()

        data object Loading : HomePageUiState()
        data object Error : HomePageUiState()
    }

    private val mUiState = MutableStateFlow<HomePageUiState>(HomePageUiState.Loading)
    val homePageUiState: StateFlow<HomePageUiState> = mUiState.asStateFlow()

    fun loadHomeScreen() {
        viewModelScope.launch {
            //TODO Chapter will be selected from user's progress
            val episodes = episodeCase.getEpisodes(Chapter.CITIES)
            mUiState.value = HomePageUiState.Loaded(episodes = episodes)
        }
    }
}