package com.vvz.brewbird.ui.views

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

fun Modifier.shimmerEffect(toShow: Boolean): Modifier = composed {

    if (toShow) {
        var size by remember {
            mutableStateOf(IntSize.Zero)
        }
        val transition = rememberInfiniteTransition(label = "shimmer_transition")
        val startOffsetX by transition.animateFloat(
            initialValue = -2 * size.width.toFloat(),
            targetValue = 2 * size.width.toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(1000)
            ), label = "shimmer_animation"
        )
        background(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFB8B5B5),
                    Color(0xFF8F8B8B),
                    Color(0xFFB8B5B5),
                ),
                start = Offset(startOffsetX, 0f),
                end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
            ),
            shape = RoundedCornerShape(5.dp)
        )
            .onGloballyPositioned {
                size = it.size
            }
    } else {
        Modifier
    }
}


fun Modifier.placeholderEffect(toShow: Boolean): Modifier = composed {

    if (toShow) {
        var size by remember {
            mutableStateOf(IntSize.Zero)
        }
        background(Color.LightGray,
                   shape = RoundedCornerShape(5.dp))
            .onGloballyPositioned {
                size = it.size
            }
    } else {
        Modifier
    }
}


fun Modifier.placeholder(show: Boolean) = this
    .placeholderEffect(show)
    .alpha(if (show) 0f else 1f)