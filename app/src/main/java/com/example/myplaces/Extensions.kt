package com.example.myplaces

import com.google.android.gms.maps.model.LatLng

fun String.toLatLng(): LatLng {
    val latLng = this.split('(', ')')[1].split(',')
    return LatLng(
        latLng[0].toDouble(),
        latLng[1].toDouble()
    )
}