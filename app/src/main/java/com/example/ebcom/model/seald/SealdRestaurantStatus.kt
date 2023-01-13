package com.example.ebcom.model.seald

sealed class SealdRestaurantStatus (var status : String){
    object OPEN  : SealdRestaurantStatus("open")
    object CLOSED : SealdRestaurantStatus("closed")
    object AHEAD : SealdRestaurantStatus("order ahead")
}