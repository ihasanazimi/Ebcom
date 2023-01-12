package com.example.ebcom.repository.restaurant_datasource

import com.example.ebcom.model.Restaurant
import com.example.ebcom.model.RestaurantsObject

interface RestaurantDataSource {

    suspend fun getRestaurantsList() : RestaurantsObject

    fun addToFavorite(restaurant: Restaurant)

    fun addAllRestaurants(list: RestaurantsObject)

    fun deleteFromFavorites (restaurant: Restaurant)

    fun updateFavorites (restaurant: Restaurant)

    fun getFavoritesRestaurants() : RestaurantsObject

    fun getOpenedRestaurants() : RestaurantsObject

    fun getOrderAheadRestaurants() : RestaurantsObject

    fun sortRestaurantsBy(restaurants: RestaurantsObject, sortType: String) : RestaurantsObject

}