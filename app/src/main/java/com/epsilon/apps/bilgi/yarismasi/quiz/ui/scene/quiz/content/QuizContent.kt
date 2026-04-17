package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.content

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsIgnoringVisibility
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.model.QuestionCategoryEnum
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonButton
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonCard
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonText
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledDp
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledSp
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun QuizContent(
    questionText: String?,
    questionCategory: QuestionCategoryEnum?,
    modifier: Modifier = Modifier,
    optionItems: List<QuizContentOptionUi>,
    isQuestionVisible: Boolean,
    showStartButton: Boolean,
    onStartClick: () -> Unit,
    onOptionClick: (optionKey: String) -> Unit,
    contentPadding: PaddingValues = PaddingValues(all = 0.nonScaledDp),
) {
    val options = remember(optionItems) { optionItems }
    val placeholderQuestionText = "???"
    val headerTargetColor = colorResource(id = questionCategory?.color ?: R.color.app_helper_color)
    val animatedHeaderColor by animateColorAsState(
        targetValue = headerTargetColor,
        animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing),
        label = "quizHeaderColor"
    )

    var animatedQuestionText by remember { mutableStateOf("") }
    LaunchedEffect(isQuestionVisible, questionText) {
        if (!isQuestionVisible) {
            animatedQuestionText = ""
            return@LaunchedEffect
        }

        val fullText = questionText.orEmpty()
        if (fullText.isEmpty()) {
            animatedQuestionText = ""
            return@LaunchedEffect
        }

        animatedQuestionText = ""
        fullText.forEach { character ->
            animatedQuestionText += character
            delay(16L)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 0.nonScaledDp),
            verticalArrangement = Arrangement.spacedBy(8.nonScaledDp)
        ) {
            stickyHeader {
                Spacer(
                    modifier = Modifier
                        .height(
                            WindowInsets.statusBarsIgnoringVisibility.asPaddingValues()
                                .calculateTopPadding()
                        )
                        .fillMaxWidth()
                        .background(color = animatedHeaderColor)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.nonScaledDp)
                        .background(color = animatedHeaderColor)
                ) {
                    questionCategory?.let {
                        EpsilonText(text = it.categoryName, textColor = R.color.app_white)
                    }
                }
            }

            item {
                EpsilonCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.nonScaledDp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.nonScaledDp, vertical = 12.nonScaledDp),
                        contentAlignment = Alignment.Center
                    ) {
                        val questionTargetText =
                            if (isQuestionVisible) questionText.orEmpty() else placeholderQuestionText
                        AnimatedContent(
                            targetState = questionTargetText,
                            transitionSpec = {
                                slideInHorizontally(
                                    animationSpec = tween(durationMillis = 300),
                                    initialOffsetX = { fullWidth -> fullWidth / 2 }
                                ) + fadeIn(animationSpec = tween(durationMillis = 230)) togetherWith
                                        slideOutHorizontally(
                                            animationSpec = tween(durationMillis = 250),
                                            targetOffsetX = { fullWidth -> -fullWidth / 2 }
                                        ) + fadeOut(animationSpec = tween(durationMillis = 190))
                            },
                            label = "questionTextTransition"
                        ) { targetText ->
                            val visibleText =
                                if (targetText == placeholderQuestionText) placeholderQuestionText else animatedQuestionText
                            EpsilonText(
                                modifier = Modifier.fillMaxWidth(),
                                text = visibleText,
                                size = 18.nonScaledSp,
                                lineHeight = 26.nonScaledSp,
                                textColor = R.color.app_main_text_color,
                                fontWeight = FontWeight.Normal,
                                textAlign = if (targetText == placeholderQuestionText) TextAlign.Center else TextAlign.Start
                            )
                        }
                    }
                }
            }

            items(options) { option ->
                EpsilonCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.nonScaledDp)
                        .clickable(enabled = option.isClickable) {
                            onOptionClick(option.key)
                        },
                    color = option.indicatorColor
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.nonScaledDp, vertical = 12.nonScaledDp)
                    ) {
                        AnimatedContent(
                            targetState = option.text,
                            transitionSpec = {
                                slideInHorizontally(
                                    animationSpec = tween(durationMillis = 280),
                                    initialOffsetX = { fullWidth -> fullWidth / 2 }
                                ) + fadeIn(animationSpec = tween(durationMillis = 220)) togetherWith
                                        slideOutHorizontally(
                                            animationSpec = tween(durationMillis = 240),
                                            targetOffsetX = { fullWidth -> -fullWidth / 2 }
                                        ) + fadeOut(animationSpec = tween(durationMillis = 180))
                            },
                            label = "optionTextTransition"
                        ) { animatedText ->
                            EpsilonText(
                                text = "${option.key}. $animatedText",
                                modifier = Modifier.fillMaxWidth(),
                                size = 16.nonScaledSp,
                                textColor = option.textColor,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = showStartButton,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 8.nonScaledDp, vertical = 8.nonScaledDp)
        ) {
            EpsilonButton(
                text = "Soruyu Gör",
                modifier = Modifier.fillMaxWidth(),
                textSize = 16.nonScaledSp,
                backgroundColor = R.color.app_button_color,
                contentPadding = PaddingValues(vertical = 10.nonScaledDp),
                fontWeight = FontWeight.Bold
            ) {
                onStartClick()
            }
        }
    }
}