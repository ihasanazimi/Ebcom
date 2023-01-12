package com.example.ebcom.model.seald

sealed class RestaurantOrderState (var status : String){
    object OPEN  : RestaurantOrderState("open")
    object CLOSED : RestaurantOrderState("closed")
    object AHEAD : RestaurantOrderState("order ahead")
}