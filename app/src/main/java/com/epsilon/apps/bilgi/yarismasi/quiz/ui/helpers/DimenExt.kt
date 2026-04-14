package com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val Int.nonScaledSp
    @Composable
    get() = (this / LocalDensity.current.fontScale).sp

val Int.nonScaledDp
    @Composable
    get() = (this * 5 / LocalDensity.current.density).dp


val Double.nonScaledDp
    @Composable
    get() = (this * 5.0 / LocalDensity.current.density.toDouble()).dp