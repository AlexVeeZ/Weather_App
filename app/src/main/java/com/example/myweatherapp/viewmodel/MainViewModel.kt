package com.example.myweatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweatherapp.repository.MainRepository
import com.example.myweatherapp.repository.MainRepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
        private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
        private val repositoryImpl: MainRepository = MainRepositoryImpl()
) :
        ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)

    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)

    fun getWeatherFromRemoteSource() = getDataFromLocalSource(isRussian = true)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(300)
            liveDataToObserve.postValue(
                    AppState.Success(
                            if (isRussian) repositoryImpl.getWeatherFromLocalStorageRus()
                            else repositoryImpl.getWeatherFromLocalStorageWorld()
                    )
            )
        }.start()
    }
}

