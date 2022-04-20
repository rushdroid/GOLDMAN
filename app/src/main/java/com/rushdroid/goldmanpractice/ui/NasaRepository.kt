package com.rushdroid.goldmanpractice.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rushdroid.goldmanpractice.model.NasaModel
import com.rushdroid.goldmanpractice.retrofit.RetrofitInterface
import com.rushdroid.goldmanpractice.retrofit.RetrofitServiceGenerator
import com.rushdroid.goldmanpractice.room.NasaDB
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NasaRepository(application: Application) {

    private var nasaModel: MutableLiveData<NasaModel> = MutableLiveData()
        get() = field


    private var db = NasaDB(application.applicationContext)
    fun getNasaModel(): LiveData<NasaModel> {
        return nasaModel
    }

    private val retrofitServiceGenerator: RetrofitServiceGenerator = RetrofitServiceGenerator()
    internal var disposable: Disposable? = null

    fun fetchNasaData(strDate: String) {
        val data = db.parentDao().getNasaDataFromDate(strDate)
        if (data.size > 0) {
            nasaModel.postValue(data.get(0))
            return
        }

        disposable = retrofitServiceGenerator.createService(RetrofitInterface::class.java)
            .getNASADetail("Lb4cjMqpf2gFLd6Tmue2fT7wEvL5Cn221lE7MHQh", strDate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                insert(it)
                val data = db.parentDao().getNasaDataFromDate(strDate)
                nasaModel.postValue(data.get(0))
            }, {
                Log.d("TAG", "fetchNasaData: ERROR" + it)
            })
    }

    fun insert(nasaModel: NasaModel) {
        db.parentDao().insert(nasaModel)
    }

    fun getDataFromDate(strDate: String): List<NasaModel>{
        val data = db.parentDao().getNasaDataFromDate(strDate)
        return data
    }

    fun update(nasaModel: NasaModel) {
        val result = db.parentDao().updateNasaModel(nasaModel)
        Log.d("TAG", "update: " + result)
    }


    fun getFav(isFav: Boolean): List<NasaModel> {
        return db.parentDao().getFavouriteData(isFav)
    }
}