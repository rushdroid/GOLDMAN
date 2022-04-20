package com.rushdroid.goldmanpractice.retrofit

import com.rushdroid.goldmanpractice.model.NasaModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("apod")
    fun getNASADetail(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): Observable<NasaModel>
}
