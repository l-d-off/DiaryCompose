package ru.darf.diarycompose.data.network

import android.content.Context
import com.google.gson.Gson
import ru.darf.diarycompose.core.utils.log.logD
import ru.darf.diarycompose.data.network.model.EventDto
import java.io.IOException

object ApiService {
    fun getEvent(context: Context): EventDto {
        lateinit var json: String

        try {
            json = context.assets.open("events.json").bufferedReader().use {
                it.readText()
            }
        } catch (e: IOException) {
            logD(this::class.java.simpleName, e.message.toString())
        }
        return Gson().fromJson(json, EventDto::class.java)
    }
}