package com.example.urbandictionary.data.model

import com.google.gson.annotations.SerializedName

data class ResponseUrban(val list: List<Define>)

data class Define(
    val definition: String?,
    val permalink: String?,
    @SerializedName("thumbs_up") val thumbsUp: Int?,
    @SerializedName("sound_urls") val soundWavUrls: List<String>?,
    val author: String?,
    val word: String?,
    val defid: Int?,
    @SerializedName("current_vote") val currentVote: String?,
    @SerializedName("written_on") val writtenOn: String?,
    val example: String?,
    @SerializedName("thumbs_down") val thumbsDown: Int?
)