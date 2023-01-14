package com.example.ebcom.services.http

import com.example.ebcom.model.RestaurantsObject
import retrofit2.http.GET

interface Api {

    @GET("46e93a3c-1d61-432e-8e70-da2f5f79353a")
    suspend fun getRestaurant() : RestaurantsObject

}