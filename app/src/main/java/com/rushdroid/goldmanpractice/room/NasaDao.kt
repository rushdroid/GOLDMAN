package com.rushdroid.goldmanpractice.room

import NasaModel
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NasaDao {

    @Insert
    fun insert(nasaModel: NasaModel)

    @Insert
    fun insertAll(nasaModel: List<NasaModel>)

    @Query("Select * from NasaTable where date= :strDate")
    fun getNasaDataFromDate(strDate: String): NasaModel


    @Query("Select * from NasaTable where isFavourite= :isFav")
    fun getFavouriteDate(isFav: Boolean): List<NasaModel>



}