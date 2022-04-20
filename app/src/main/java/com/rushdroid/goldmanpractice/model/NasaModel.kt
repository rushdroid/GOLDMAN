package com.rushdroid.goldmanpractice.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "NasaModel")
data class NasaModel(
    @PrimaryKey(autoGenerate = true)
    var uId: Int = -1,
    var isFavourite: Boolean = false,
    @SerializedName("copyright") val copyright: String,
    @SerializedName("date") val date: String,
    @SerializedName("explanation") val explanation: String,
    @SerializedName("hdurl") val hdurl: String,
    @SerializedName("media_type") val media_type: String,
    @SerializedName("service_version") val service_version: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String
)