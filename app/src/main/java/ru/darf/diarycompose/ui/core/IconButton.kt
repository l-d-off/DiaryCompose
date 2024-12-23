package ru.darf.diarycompose.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.darf.diarycompose.core.ext.clickableNoRipple
import ru.darf.diarycompose.core.ext.clickableRipple
import ru.darf.diarycompose.ui.theme.AppTheme

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    size: Dp = 24.dp,
    tint: Color = AppTheme.colors.textPrimary,
    backgroundColor: Color = AppTheme.colors.sky,
    hasRipple: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    borderColor: Color = Color.Transparent,
    onClick: () -> Unit = {},
    shape: Shape = CircleShape,
    enabled: Boolean = true,
) {
    val finalBorderColor = if (enabled) borderColor else borderColor.copy(0.5f)
    val finalBackgroundColor = if (enabled) backgroundColor else backgroundColor.copy(0.5f)

    Icon(
        modifier = modifier
            .clip(shape)
            .border(1.dp, finalBorderColor, shape)
            .then(
                if (enabled) {
                    if (hasRipple) Modifier.clickableRipple(onClick = onClick)
                    else Modifier.clickableNoRipple(onClick = onClick)
                } else {
                    Modifier
                }
            )
            .background(finalBackgroundColor)
            .padding(contentPadding)
            .size(size),
        imageVector = imageVector,
        tint = tint,
        contentDescription = null
    )
}