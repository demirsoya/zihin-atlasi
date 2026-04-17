package com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.convexBorder
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledDp
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledSp
import kotlinx.coroutines.delay

@Composable
fun EpsilonDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    canBeDismissed: Boolean = true,
    size: Float = 1f,
    content: @Composable () -> Unit,
) {
    val dialogExitDurationMillis = 130L
    var showAnimatedDialog by remember { mutableStateOf(false) }
    var retainedContent by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }
    var dismissRequestedByUser by remember { mutableStateOf(false) }

    LaunchedEffect(showDialog) {
        if (showDialog) {
            showAnimatedDialog = true
            retainedContent = content
            dismissRequestedByUser = false
        } else {
            dismissRequestedByUser = false
            delay(dialogExitDurationMillis)
            retainedContent = null
        }
    }

    LaunchedEffect(dismissRequestedByUser, showDialog) {
        if (dismissRequestedByUser && showDialog) {
            delay(dialogExitDurationMillis)
            onDismissRequest()
            dismissRequestedByUser = false
        }
    }

    LaunchedEffect(content, showDialog) {
        if (showDialog) retainedContent = content
    }

    if (showAnimatedDialog) {
        Dialog(
            onDismissRequest = {
                if (canBeDismissed) {
                    dismissRequestedByUser = true
                }
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            (LocalView.current.parent as? DialogWindowProvider)?.window?.let { window ->
                window.setDimAmount(0.56f)
                window.setWindowAnimations(-1)
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                var animateIn by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) { animateIn = true }
                AnimatedVisibility(
                    visible = animateIn && showDialog && !dismissRequestedByUser,
                    enter = fadeIn(animationSpec = tween(durationMillis = 160)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 120)),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectTapGestures {
                                    if (canBeDismissed) {
                                        dismissRequestedByUser = true

                                    }
                                }
                            }
                            .background(colorResource(id = R.color.app_black).copy(alpha = .56f))
                    )
                }

                AnimatedVisibility(
                    visible = animateIn && showDialog && !dismissRequestedByUser,
                    enter = scaleIn(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    ),
                    exit = scaleOut(animationSpec = tween(durationMillis = 130))
                ) {
                    Box(
                        modifier = Modifier
                            .pointerInput(Unit) { detectTapGestures { } }
                            .fillMaxWidth(size)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                color = colorResource(R.color.app_trans)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        (retainedContent ?: content).invoke()
                    }

                    DisposableEffect(Unit) {
                        onDispose {
                            showAnimatedDialog = false
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun EpsilonDialogContainer(title: String? = null, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.85f)
            .convexBorder(
                color = colorResource(R.color.app_helper_color),
                strokeWidth = 3.nonScaledDp,
                shape = RoundedCornerShape(4.nonScaledDp)
            ),
        elevation = CardDefaults.cardElevation(4.nonScaledDp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.app_white)
        )
    ) {
        title?.let {
            EpsilonText(
                text = title,
                textColor = R.color.app_black,
                size = 16.nonScaledSp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(R.color.app_helper_color))
                    .padding(
                        end = 4.nonScaledDp,
                        start = 4.nonScaledDp,
                        bottom = 4.nonScaledDp,
                        top = 6.nonScaledDp
                    )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            content()
            Spacer(modifier = Modifier.padding(6.nonScaledDp))
        }
    }
}