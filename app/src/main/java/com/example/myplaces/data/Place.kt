package com.example.myplaces.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place_table")
class Place(@PrimaryKey @ColumnInfo(name = "place") val name: String,@ColumnInfo(name = "destination") val destination : String)