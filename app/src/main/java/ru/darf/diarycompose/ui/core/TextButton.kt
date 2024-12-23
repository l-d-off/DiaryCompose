package ru.darf.diarycompose.ui.core

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import ru.darf.diarycompose.core.ext.clickableNoRipple
import ru.darf.diarycompose.core.ext.clickableRipple
import ru.darf.diarycompose.ui.theme.AppTheme

@Composable
fun TextButton(
    text: String,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp),
    textColor: Color = AppTheme.colors.textPrimary,
    hasRipple: Boolean = true,
    onClick: () -> Unit = {},
) {
    Text(
        modifier = Modifier
            .then(
                if (hasRipple) Modifier.clickableRipple(onClick = onClick)
                else Modifier.clickableNoRipple(onClick = onClick)
            ),
        text = text,
        style = textStyle,
        color = textColor
    )
}