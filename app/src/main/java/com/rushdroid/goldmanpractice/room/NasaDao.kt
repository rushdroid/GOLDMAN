package com.rushdroid.goldmanpractice.room

import androidx.room.*
import com.rushdroid.goldmanpractice.model.NasaModel

@Dao
interface NasaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(nasaModel: NasaModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(nasaModel: List<NasaModel>)

    @Query("Select * from NasaModel where date= :strDate")
    fun getNasaDataFromDate(strDate: String): List<NasaModel>


    @Query("Select * from NasaModel where isFavourite= :isFav")
    fun getFavouriteData(isFav: Boolean): List<NasaModel>

    @Update
    fun updateNasaModel(nasaModel: NasaModel)

    @Delete
    fun deleteNasa(nasaModel: NasaModel)
}