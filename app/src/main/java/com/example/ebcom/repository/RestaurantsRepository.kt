package com.example.ebcom.repository

import com.example.ebcom.model.Restaurant
import com.example.ebcom.model.RestaurantsObject

interface RestaurantsRepository {

    suspend fun getAllRestaurantsList() : RestaurantsObject


    fun addToFavorite(restaurant: Restaurant)

    fun addAllRestaurants(listObj: RestaurantsObject)

    fun deleteFromFavorites (restaurant: Restaurant)

    fun updateFavorites (restaurant: Restaurant)

    fun getFavoritesListRestaurants() : RestaurantsObject

    fun getOpenedRestaurants() : RestaurantsObject

    fun getOrderAheadRestaurants() : RestaurantsObject

    suspend fun sortRestaurantsBy(restaurants: RestaurantsObject, sortType: String) : RestaurantsObject

}