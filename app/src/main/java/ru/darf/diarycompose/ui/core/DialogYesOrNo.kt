package ru.darf.diarycompose.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.darf.diarycompose.R
import ru.darf.diarycompose.core.ext.asString
import ru.darf.diarycompose.ui.theme.AppTheme

@Composable
fun DialogYesOrNo(
    title: String,
    onYesClick: () -> Unit,
    onNoClick: () -> Unit,
) {
    Dialog(onDismissRequest = onNoClick) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(AppTheme.colors.backgroundSecondary)
                .border(1.dp, AppTheme.colors.sky, RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = title,
                style = TextStyle(
                    fontWeight = FontWeight.W600,
                    fontSize = 32.sp
                ),
                color = AppTheme.colors.sky
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AppButton(
                    modifier = Modifier.weight(1f),
                    text = R.string.yes.asString(),
                    onClick = onYesClick,
                    contentColor = AppTheme.colors.sky
                )
                AppButton(
                    modifier = Modifier.weight(1f),
                    text = R.string.no.asString(),
                    onClick = onNoClick,
                    contentColor = AppTheme.colors.sky.copy(0.5f)
                )
            }
        }
    }
}
