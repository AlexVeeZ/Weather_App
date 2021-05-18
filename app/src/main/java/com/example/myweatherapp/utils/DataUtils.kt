package com.example.myweatherapp.utils

import com.example.myweatherapp.model.FactDTO
import com.example.myweatherapp.model.Weather
import com.example.myweatherapp.model.WeatherDTO
import com.example.myweatherapp.model.getDefaultCity

fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
    val fact: FactDTO = weatherDTO.fact!!
    return listOf(Weather(getDefaultCity(), fact.temp!!,
        fact.feelslike!!, fact.condition!!, fact.icon))
}