package com.rushdroid.goldmanpractice.ui

import NasaModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class DetailViewModel(application: Application) : AndroidViewModel(application) {


    private val nasaRepository = NasaRepository(application)

    fun getNasaModel(): LiveData<NasaModel> {
        return nasaRepository.getNasaModel()
    }

    fun fetchNasaData(strDate: String) {
        nasaRepository.fetchNasaData(strDate)
    }

    fun insertAll(list: List<NasaModel>) {
        nasaRepository.insertAll(list)
    }

    override fun onCleared() {
        super.onCleared()
        if (nasaRepository.disposable != null && !nasaRepository.disposable!!.isDisposed) {
            nasaRepository.disposable!!.dispose()
        }
    }
}