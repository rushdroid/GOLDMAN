package com.rushdroid.goldmanpractice.ui

import NasaModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rushdroid.goldmanpractice.retrofit.RetrofitInterface
import com.rushdroid.goldmanpractice.retrofit.RetrofitServiceGenerator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailViewModel : ViewModel() {

    private var nasaModel: MutableLiveData<NasaModel> = MutableLiveData()
        get() = field

    fun getNasaModel(): LiveData<NasaModel> {
        return nasaModel
    }

    private val retrofitServiceGenerator: RetrofitServiceGenerator = RetrofitServiceGenerator()
    private var disposable: Disposable? = null



    fun fetchNasaData(strDate: String) {
        disposable = retrofitServiceGenerator.createService(RetrofitInterface::class.java)
            .getNASADetail("Lb4cjMqpf2gFLd6Tmue2fT7wEvL5Cn221lE7MHQh",strDate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                nasaModel.postValue(it)
            }, {
                Log.d("TAG", "fetchNasaData: ERROR" + it)
            })
    }

    override fun onCleared() {
        super.onCleared()
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }
}