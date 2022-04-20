package com.rushdroid.goldmanpractice.ui

import NasaModel
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

        disposable = retrofitServiceGenerator.createService(RetrofitInterface::class.java)
            .getNASADetail("Lb4cjMqpf2gFLd6Tmue2fT7wEvL5Cn221lE7MHQh", strDate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                nasaModel.postValue(it)
            }, {
                Log.d("TAG", "fetchNasaData: ERROR" + it)
            })
    }

    fun insertAll(list: List<NasaModel>) {
        db.parentDao().insertAll(list)
    }
}