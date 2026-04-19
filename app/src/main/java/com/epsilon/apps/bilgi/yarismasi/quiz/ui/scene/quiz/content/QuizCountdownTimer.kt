package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.content

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class QuizCountdownTimer {

    fun start(
        scope: CoroutineScope,
        totalSeconds: Int,
        onTick: (Int) -> Unit,
        onFinished: () -> Unit
    ): Job {
        return scope.launch {
            var remainingSeconds = totalSeconds.coerceAtLeast(0)
            onTick(remainingSeconds)

            while (isActive && remainingSeconds > 0) {
                delay(1_000L)
                if (!isActive) return@launch
                remainingSeconds -= 1
                onTick(remainingSeconds)
            }

            if (isActive && remainingSeconds == 0) {
                onFinished()
            }
        }
    }
}
