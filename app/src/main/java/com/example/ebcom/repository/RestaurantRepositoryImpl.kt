package com.example.ebcom.repository

import com.example.ebcom.model.Restaurant
import com.example.ebcom.model.RestaurantsObject
import com.example.ebcom.model.seald.SealdSortValues
import com.example.ebcom.repository.restaurant_datasource.local.RestaurantLocalDataSource
import com.example.ebcom.repository.restaurant_datasource.remote.RestaurantRemoteDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RestaurantRepositoryImpl(
    private val remoteDataSource: RestaurantRemoteDataSource,
    private val localDataSource: RestaurantLocalDataSource
) : RestaurantsRepository {

    override suspend fun getAllRestaurantsList(): RestaurantsObject {
        if (localDataSource.getRestaurantsList().restaurants.isNotEmpty())
            return localDataSource.sortRestaurantsBy(localDataSource.getRestaurantsList(),SealdSortValues.BestMatch.sortKey)
        val remoteResultObject = remoteDataSource.getRestaurantsList()
        return remoteDataSource.sortRestaurantsBy(remoteResultObject,SealdSortValues.BestMatch.sortKey)
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

    override suspend fun sortRestaurantsBy(restaurants: RestaurantsObject, sortType: String): RestaurantsObject {
        var resObj : RestaurantsObject ? = null
        val job = GlobalScope.launch {
            resObj = if (localDataSource.getRestaurantsList().restaurants.isNotEmpty()) localDataSource.sortRestaurantsBy(restaurants, sortType)
            else remoteDataSource.getRestaurantsList()
        }
        job.join()
        return localDataSource.sortRestaurantsBy(resObj!!, sortType)
    }


}