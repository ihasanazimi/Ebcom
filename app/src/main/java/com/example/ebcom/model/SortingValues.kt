package com.example.ebcom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SortingValues(
    @PrimaryKey
    @ColumnInfo(name = "bestMatch")
    val bestMatch: Double = 0.0,
    @ColumnInfo(name = "newest")
    val newest: Double = 0.0,
    @ColumnInfo(name = "ratingAverage")
    val ratingAverage: Double = 0.0,
    @ColumnInfo(name = "distance")
    val distance: Int = 0,
    @ColumnInfo(name = "popularity")
    val popularity: Double = 0.0,
    @ColumnInfo(name = "averageProductPrice")
    val averageProductPrice: Int = 0,
    @ColumnInfo(name = "deliveryCosts")
    val deliveryCosts: Int = 0,
    @ColumnInfo(name = "minCost")
    val minCost: Int = 0
){

    fun distanceByKM() = "Distance : " + String.format("%.1f", distance / 1000F)+"KM"
    fun ratingAverageFlt() = ratingAverage.toFloat()
    fun popularityStr() = "(${popularity.toInt()})"
    fun deliveryCostsStr() = if (deliveryCosts==0) "Free" else "$deliveryCosts$"
    fun newest() = "$newest"
    fun bestMatch() = "${bestMatch.toInt()}"
    fun minCost() = "$minCost"
    fun averageProductPrice() = "$averageProductPrice$"

}