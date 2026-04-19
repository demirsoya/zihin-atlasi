package com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases

import com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.content.QuizCountdownTimer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

class QuizQuestionTimerCase(
    private val quizCountdownTimer: QuizCountdownTimer
) {

    fun startTimer(
        scope: CoroutineScope,
        totalSeconds: Int,
        onTick: (Int) -> Unit,
        onTimeout: () -> Unit
    ): Job {
        return quizCountdownTimer.start(
            scope = scope,
            totalSeconds = totalSeconds,
            onTick = onTick,
            onFinished = onTimeout
        )
    }
}
