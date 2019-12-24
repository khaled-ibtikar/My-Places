package com.example.myplaces.features.map

import com.example.myplaces.data.Place
import com.example.myplaces.data.PlaceDao

class PlaceRepository(private val placeDao: PlaceDao) {

    suspend fun insertPlace(place: Place) {
        placeDao.insertPlace(place)
    }

}