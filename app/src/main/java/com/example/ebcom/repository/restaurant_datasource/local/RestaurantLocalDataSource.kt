package com.example.ebcom.repository.restaurant_datasource.local

import com.example.ebcom.db.RoomDB
import com.example.ebcom.model.Restaurant
import com.example.ebcom.model.RestaurantsObject
import com.example.ebcom.model.seald.RestaurantOrderState
import com.example.ebcom.model.seald.SortValues
import com.example.ebcom.repository.restaurant_datasource.RestaurantDataSource

class RestaurantLocalDataSource(private val db : RoomDB) : RestaurantDataSource {

    override suspend fun getRestaurantsList(): RestaurantsObject {
        val objList = db.restaurantDao().allRestaurants()
        return RestaurantsObject(objList)
    }

    override fun addToFavorite(restaurant: Restaurant) {
        db.restaurantDao().updateRestaurantState(restaurant)
    }

    override fun addAllRestaurants(list: RestaurantsObject) {
        list.restaurants.forEach{
            db.restaurantDao().insertRestaurant(it)
        }
    }

    override fun deleteFromFavorites(restaurant: Restaurant) {
        db.restaurantDao().deleteRestaurant(restaurant)
    }

    override fun updateFavorites(restaurant: Restaurant) {
        db.restaurantDao().updateRestaurantState(restaurant)
    }

    override fun getFavoritesRestaurants(): RestaurantsObject {
        val objList = db.restaurantDao().getAllFavoritesRestaurants()
        return RestaurantsObject(objList)
    }

    override fun getOpenedRestaurants(): RestaurantsObject {
        val res = db.restaurantDao().allRestaurants().filter { it.status == RestaurantOrderState.OPEN.status }
        return RestaurantsObject(res)
    }

    override fun getOrderAheadRestaurants(): RestaurantsObject {
        val res = db.restaurantDao().allRestaurants().filter { it.status == RestaurantOrderState.AHEAD.status }
        return RestaurantsObject(res)
    }

    override fun sortRestaurantsBy(restaurants: RestaurantsObject, sortType: String): RestaurantsObject {
        val list = when(sortType) {
            SortValues.BestMatch.sortKey -> restaurants.restaurants.sortedWith(compareBy { it.sortingValues?.bestMatch })
            SortValues.NEWEST.sortKey -> restaurants.restaurants.sortedWith(compareBy { it.sortingValues?.newest })
            SortValues.RatingAverage.sortKey -> restaurants.restaurants.sortedWith(compareBy { it.sortingValues?.ratingAverage })
            SortValues.Distance.sortKey -> restaurants.restaurants.sortedWith(compareBy { it.sortingValues?.distance })
            SortValues.POPULARITY.sortKey -> restaurants.restaurants.sortedWith(compareBy { it.sortingValues?.popularity })
            SortValues.AverageProductPrice.sortKey -> restaurants.restaurants.sortedWith(compareBy { it.sortingValues?.averageProductPrice })
            SortValues.DeliveryCosts.sortKey -> restaurants.restaurants.sortedWith(compareBy { it.sortingValues?.deliveryCosts })
            SortValues.MinCost.sortKey -> restaurants.restaurants.sortedWith(compareBy { it.sortingValues?.minCost })
            else-> { arrayListOf() }
        }

        list.sortedByDescending { it.favorite }
        return RestaurantsObject(list)
    }


}