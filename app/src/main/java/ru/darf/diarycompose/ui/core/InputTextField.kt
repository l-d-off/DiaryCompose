package ru.darf.diarycompose.ui.core

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.darf.diarycompose.core.ext.rememberState
import ru.darf.diarycompose.ui.theme.AppTheme

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onFocusedChange: (FocusState) -> Unit,
    onDoneClick: () -> Unit = {},
    placeholder: String = "",
    singleLine: Boolean = true,
    placeholderColor: Color = AppTheme.colors.textSecondary,
    textColor: Color = AppTheme.colors.textField,
    containerColor: Color = AppTheme.colors.backgroundPrimary,
    indicatorColor: Color = Color.Transparent,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp),
    keyboardType: KeyboardType = KeyboardType.Text,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    borderColor: Color = AppTheme.colors.backgroundSecondary,
    focusedBorderColor: Color = AppTheme.colors.textPrimary,
    focusManager: FocusManager? = null,
) {
    val imeHelp = focusManager ?: LocalFocusManager.current
    var focused by rememberState { false }
    val currentBorderColor = if (focused) focusedBorderColor else borderColor

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .border(2.dp, currentBorderColor, RoundedCornerShape(4.dp))
            .onFocusChanged {
                focused = it.isFocused
                onFocusedChange(it)
            },
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = {
            if (placeholder.isNotBlank()) {
                Text(
                    color = placeholderColor,
                    text = placeholder,
                    style = textStyle
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (value.text.isNotBlank()) onDoneClick()
                imeHelp.clearFocus()
            },
        ),
        singleLine = singleLine,
        textStyle = textStyle,
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = textColor,
            disabledTextColor = textColor,
            focusedTextColor = textColor,
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            focusedIndicatorColor = indicatorColor,
            unfocusedIndicatorColor = indicatorColor,
            disabledIndicatorColor = indicatorColor
        ),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon
    )
}
