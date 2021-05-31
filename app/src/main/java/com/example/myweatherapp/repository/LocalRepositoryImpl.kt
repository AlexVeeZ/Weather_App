package com.example.myweatherapp.repository

import com.example.myweatherapp.model.Weather
import com.example.myweatherapp.room.HistoryDao
import com.example.myweatherapp.utils.convertHistoryEntityToWeather
import com.example.myweatherapp.utils.convertWeatherToEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDao) :
    LocalRepository {

    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToEntity(weather))
    }
}