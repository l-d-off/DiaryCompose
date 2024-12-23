package ru.darf.diarycompose.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.darf.diarycompose.core.ext.clickableNoRipple
import ru.darf.diarycompose.ui.theme.AppTheme

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    textTitle: String = "",
    textStyle: TextStyle = TextStyle(fontSize = 20.sp),
    textAlign: TextAlign = TextAlign.Start,
    onTextClick: (() -> Unit)? = null,
    nearTextContent: (@Composable () -> Unit)? = null,
    leftContent: (@Composable () -> Unit)? = null,
    rightContent: (@Composable () -> Unit)? = null,
    contentColor: Color = AppTheme.colors.textPrimary,
    containerColor: Color = AppTheme.colors.backgroundPrimary,
    containerHeight: Dp = 56.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(16.dp),
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(containerHeight)
            .background(containerColor)
            .padding(contentPadding),
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement
    ) {
        leftContent?.invoke()

        Row(
            modifier = Modifier
                .weight(1f)
                .then(
                    if (onTextClick == null) Modifier else Modifier.clickableNoRipple(
                        onTextClick
                    )
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                textAlign = textAlign,
                color = contentColor,
                style = textStyle,
                text = textTitle
            )
            nearTextContent?.invoke()
        }

        rightContent?.invoke()
    }
}