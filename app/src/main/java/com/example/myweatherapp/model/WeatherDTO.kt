package com.example.myweatherapp.model

import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    val fact: FactDTO?
)

data class FactDTO(
    val temp: Int?,
    @SerializedName("feels_like")
    val feelslike: Int?,
    val condition: String?,
    val icon: String?
)

