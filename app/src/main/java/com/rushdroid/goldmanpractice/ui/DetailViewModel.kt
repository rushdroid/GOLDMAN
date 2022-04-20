package com.rushdroid.goldmanpractice.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.rushdroid.goldmanpractice.model.NasaModel

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val nasaRepository = NasaRepository(application)

    fun getNasaModel(): LiveData<NasaModel> {
        return nasaRepository.getNasaModel()
    }

    fun fetchNasaData(strDate: String) {
        nasaRepository.fetchNasaData(strDate)
    }

    fun getDataFromDate(strDate: String): List<NasaModel>{
        return  nasaRepository.getDataFromDate(strDate)
    }


    fun insert(nasaModel: NasaModel) {
        nasaRepository.insert(nasaModel)
    }

    fun delete(nasaModel: NasaModel) {
        nasaRepository.insert(nasaModel)
    }

    fun update(nasaModel: NasaModel) {
        nasaRepository.update(nasaModel)
    }

    fun getFav(isFav: Boolean): List<NasaModel> {
        return nasaRepository.getFav(isFav)
    }


    override fun onCleared() {
        super.onCleared()
        if (nasaRepository.disposable != null && !nasaRepository.disposable!!.isDisposed) {
            nasaRepository.disposable!!.dispose()
        }
    }
}