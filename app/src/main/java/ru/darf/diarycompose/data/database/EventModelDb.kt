package ru.darf.diarycompose.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventModelDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val dateStart: Long,
    val dateFinish: Long,
    val name: String,
    val description: String,
)