package com.example.ebcom.repository

import com.example.ebcom.model.Restaurant
import com.example.ebcom.model.RestaurantsObject
import com.example.ebcom.repository.restaurant_datasource.RestaurantDataSource
import com.example.ebcom.repository.restaurant_datasource.local.RestaurantLocalDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RestaurantRepositoryImpl(
    private val remoteDataSource: RestaurantDataSource,
    private val localDataSource: RestaurantLocalDataSource
) : RestaurantsRepository {

    override suspend fun getAllRestaurantsList(): RestaurantsObject {
        if (localDataSource.getRestaurantsList().restaurants.isNotEmpty()) return localDataSource.getRestaurantsList()
        return remoteDataSource.getRestaurantsList()
    }

    override fun addToFavorite(restaurant: Restaurant) {
        localDataSource.addToFavorite(restaurant)
    }

    override fun addAllRestaurants(listObj: RestaurantsObject) {
        localDataSource.addAllRestaurants(listObj)
    }

    override fun deleteFromFavorites(restaurant: Restaurant) {
        localDataSource.deleteFromFavorites(restaurant)
    }

    override fun updateFavorites(restaurant: Restaurant) {
        localDataSource.updateFavorites(restaurant)
    }

    override fun getFavoritesListRestaurants(): RestaurantsObject {
        return localDataSource.getFavoritesRestaurants()
    }

    override fun getOpenedRestaurants(): RestaurantsObject {
        return localDataSource.getOpenedRestaurants()
    }

    override fun getOrderAheadRestaurants(): RestaurantsObject {
        return localDataSource.getOrderAheadRestaurants()
    }

    override fun sortRestaurantsBy(
        restaurants: RestaurantsObject,
        sortType: String
    ): RestaurantsObject {
        return localDataSource.sortRestaurantsBy(restaurants, sortType)
    }


}