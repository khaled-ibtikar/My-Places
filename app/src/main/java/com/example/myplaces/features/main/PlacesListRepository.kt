package com.example.myplaces.features.main

import androidx.lifecycle.LiveData
import com.example.myplaces.data.Place
import com.example.myplaces.data.PlaceDao

class PlacesListRepository(private val placeDao: PlaceDao) {

    val allPlaces: LiveData<List<Place>> = placeDao.getPlaces()

    suspend fun deletePlace(place: String) {
        placeDao.deleteWord(place)
    }
}