package ru.darf.diarycompose.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.darf.diarycompose.core.ext.clickableNoRipple
import ru.darf.diarycompose.core.ext.clickableRipple
import ru.darf.diarycompose.ui.theme.AppTheme

@Composable
fun AppButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(4.dp),
    backgroundColor: Color = AppTheme.colors.backgroundSecondary,
    contentColor: Color = AppTheme.colors.sky,
    backgroundDisableColor: Color = AppTheme.colors.backgroundSecondary,
    contentDisableColor: Color = AppTheme.colors.sky,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp),
    contentPadding: PaddingValues = PaddingValues(vertical = 12.dp),
    hasRipple: Boolean = false,
) {
    val contentColorFinal = if (enabled) contentColor else contentDisableColor
    val backgroundColorFinal = if (enabled) backgroundColor else backgroundDisableColor

    Text(
        modifier = modifier
            .clip(shape)
            .background(backgroundColorFinal, shape)
            .then(
                if (hasRipple) Modifier.clickableRipple(onClick = onClick)
                else Modifier.clickableNoRipple(onClick = onClick)
            )
            .padding(contentPadding),
        text = text,
        maxLines = 1,
        textAlign = TextAlign.Center,
        style = textStyle,
        color = contentColorFinal
    )
}