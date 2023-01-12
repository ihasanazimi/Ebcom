package com.example.ebcom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class RestaurantsObject (
    @PrimaryKey
    @ColumnInfo(name = "restaurants")
    val restaurants: List<Restaurant>
    )