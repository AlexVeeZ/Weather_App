package com.example.myweatherapp.repository

import com.example.myweatherapp.model.Weather

interface LocalRepository {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)
}