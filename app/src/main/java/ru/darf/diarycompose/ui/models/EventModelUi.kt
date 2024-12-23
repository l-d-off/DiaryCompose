package ru.darf.diarycompose.ui.models

import androidx.compose.runtime.Immutable

@Immutable
data class EventModelUi(
    val id: String,
    val dateStart: Long,
    val dateFinish: Long,
    val name: String,
    val description: String,
)
