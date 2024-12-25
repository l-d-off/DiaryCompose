package ru.darf.diarycompose.data.network.model

import com.google.gson.annotations.SerializedName

data class EventDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("date_start")
    val dateStart: Long,
    @SerializedName("date_finish")
    val dateFinish: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
)