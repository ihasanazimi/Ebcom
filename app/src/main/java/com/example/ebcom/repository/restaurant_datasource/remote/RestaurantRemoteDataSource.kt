package com.example.ebcom.repository.restaurant_datasource.remote

import com.example.ebcom.model.Restaurant
import com.example.ebcom.model.RestaurantsObject
import com.example.ebcom.repository.restaurant_datasource.RestaurantDataSource
import com.example.ebcom.services.http.ApiService
import kotlinx.coroutines.DelicateCoroutinesApi

class RestaurantRemoteDataSource(private val api : ApiService) : RestaurantDataSource {

    override suspend fun getRestaurantsList(): RestaurantsObject {
        return api.getRestaurant()
    }

    override fun addToFavorite(restaurant: Restaurant) {
        TODO("Not yet implemented")
    }

    override fun addAllRestaurants(list: RestaurantsObject) {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorites(restaurant: Restaurant) {
        TODO("Not yet implemented")
    }

    override fun updateFavorites(restaurant: Restaurant) {
        TODO("Not yet implemented")
    }

    override fun getFavoritesRestaurants(): RestaurantsObject {
        TODO("Not yet implemented")
    }

    override fun getOpenedRestaurants(): RestaurantsObject {
        TODO("Not yet implemented")
    }

    override fun getOrderAheadRestaurants(): RestaurantsObject {
        TODO("Not yet implemented")
    }

    override fun sortRestaurantsBy(restaurants: RestaurantsObject, sortType: String): RestaurantsObject {
        TODO("Not yet implemented")
    }


}