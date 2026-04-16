package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.homepage

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epsilon.apps.bilgi.yarismasi.quiz.model.ChapterEnum
import com.epsilon.apps.bilgi.yarismasi.quiz.model.UiEpisode
import com.epsilon.apps.bilgi.yarismasi.quiz.model.User
import com.epsilon.apps.bilgi.yarismasi.quiz.model.UserProgress
import com.epsilon.apps.bilgi.yarismasi.quiz.room.AppDatabase
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases.ChapterCase
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases.EpisodeCase
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases.UserCase
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases.UserProgressCase
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
            episodeCase = EpisodeCase(),
            chapterCase = ChapterCase(),
            userCase = UserCase(appDatabase = appDatabase),
            userProgressCase = UserProgressCase(appDatabase = appDatabase)
        ) as T
    })
}

class HomePageViewModel(
    private val appDatabase: AppDatabase,
    private val episodeCase: EpisodeCase,
    private val chapterCase: ChapterCase,
    private val userCase: UserCase,
    private val userProgressCase: UserProgressCase,
) : ViewModel() {

    sealed class HomePageUiState {
        data class Loaded(
            val user: User,
            val chapter: ChapterEnum,
            val episodes: List<UiEpisode>,
            val userProgress: UserProgress
        ) : HomePageUiState()

        data object Loading : HomePageUiState()
        data object Error : HomePageUiState()
    }

    private val mUiState = MutableStateFlow<HomePageUiState>(HomePageUiState.Loading)
    val homePageUiState: StateFlow<HomePageUiState> = mUiState.asStateFlow()

    fun loadHomePage() {
        viewModelScope.launch {
            runCatching {
                val user = userCase.getUser()
                val progress = userProgressCase.getUserProgress()
                val chapter = chapterCase.getChapterById(progress.chapter)
                val episodes = episodeCase.applyUserProgress(
                    episodes = episodeCase.getEpisodes(chapter),
                    userProgress = progress
                )

                HomePageUiState.Loaded(
                    user = user,
                    chapter = chapter,
                    episodes = episodes,
                    userProgress = progress
                )
            }.onSuccess { loaded ->
                mUiState.value = loaded
            }.onFailure {
                mUiState.value = HomePageUiState.Error
            }
        }
    }
}