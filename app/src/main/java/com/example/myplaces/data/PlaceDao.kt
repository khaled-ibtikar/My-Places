package com.example.myplaces.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaceDao {

    @Query("SELECT * from place_table")
    fun getPlaces(): LiveData<List<Place>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlace(place: Place)

    @Query("DELETE FROM place_table WHERE place = :place")
    suspend fun deleteWord(place: String)
}