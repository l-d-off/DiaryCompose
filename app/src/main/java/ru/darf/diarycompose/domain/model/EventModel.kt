package ru.darf.diarycompose.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventModel(
    val id: Int = -1,
    val dateStart: String,
    val dateFinish: String,
    val name: String,
    val description: String,
) : Parcelable