package ru.darf.diarycompose.core.ext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.clickableRipple(
    onClick: () -> Unit,
    color: Color = Color.Unspecified,
) = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple(color = color),
        onClick = onClick,
    )
}

fun Modifier.clickableNoRipple(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
}

fun Modifier.ignoreParentPadding(
    horizontal: Dp = 0.dp,
    vertical: Dp = 0.dp,
): Modifier = this.layout { measurable, constraints ->
    val newWidth = constraints.maxWidth + 2 * horizontal.roundToPx()
    val newHeight = constraints.maxHeight + 2 * vertical.roundToPx()
    val placeable = measurable.measure(
        constraints.copy(
            maxWidth = newWidth,
            maxHeight = newHeight
        )
    )
    layout(placeable.width, placeable.height) {
        placeable.place(0, 0)
    }
}