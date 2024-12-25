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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.darf.diarycompose.R
import ru.darf.diarycompose.core.ext.asString
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
    textColor: Color = AppTheme.colors.textPrimary,
    containerColor: Color = AppTheme.colors.textField,
    indicatorColor: Color = Color.Transparent,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp),
    keyboardType: KeyboardType = KeyboardType.Text,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    borderColor: Color = AppTheme.colors.backgroundSecondary,
    focusedBorderColor: Color = AppTheme.colors.sky,
    focusManager: FocusManager? = null,
    cursorColor: Color = textColor,
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
            disabledIndicatorColor = indicatorColor,
            cursorColor = cursorColor
        ),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon
    )
}

@Composable
fun TimeRangeTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onFocusedChange: (FocusState) -> Unit,
    onDoneClick: () -> Unit = {},
    placeholder: String = "",
    singleLine: Boolean = true,
    placeholderColor: Color = AppTheme.colors.textSecondary,
    textColor: Color = AppTheme.colors.textPrimary,
    containerColor: Color = AppTheme.colors.textField,
    indicatorColor: Color = Color.Transparent,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp),
    keyboardType: KeyboardType = KeyboardType.Text,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    borderColor: Color = AppTheme.colors.backgroundSecondary,
    focusedBorderColor: Color = AppTheme.colors.sky,
    focusManager: FocusManager? = null,
    cursorColor: Color = textColor,
) {
    val imeHelp = focusManager ?: LocalFocusManager.current
    var focused by rememberState { false }
    val currentBorderColor = if (focused) focusedBorderColor else borderColor
    val defaultTimeRange = R.string.default_time_range.asString()

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
        onValueChange = { newValue ->
            if (Regex("[0-9]{0,4}").matches(newValue.text)) {
                if (newValue.text.length < 4) {
                    onValueChange(newValue)
                } else if (
                    newValue.text.take(2).toInt() < newValue.text.drop(2).toInt() &&
                    newValue.text.drop(2).toInt() <= 23
                ) {
                    onValueChange(newValue)
                } else {
                    onValueChange(newValue.copy(text = defaultTimeRange))
                }
            }
        },
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
            disabledIndicatorColor = indicatorColor,
            cursorColor = cursorColor
        ),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = timeRangeVisualTransformation()
    )
}

private fun timeRangeVisualTransformation(): VisualTransformation {
    return VisualTransformation { text ->
        val inputText = text.text
        val formattedText = when {
            inputText.length >= 2 -> inputText.take(2) + "-" + inputText.drop(2)
            else -> inputText
        }
        TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return if (offset < 2) offset else offset + 1
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return if (offset < 2) offset else offset - 1
                }
            }
        )
    }
}