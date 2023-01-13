package com.example.ebcom.repository.restaurant_datasource.local

import android.util.Log
import com.example.ebcom.db.RoomDB
import com.example.ebcom.model.Restaurant
import com.example.ebcom.model.RestaurantsObject
import com.example.ebcom.model.seald.RestaurantOrderState
import com.example.ebcom.model.seald.SortValues
import com.example.ebcom.repository.restaurant_datasource.RestaurantDataSource
import kotlin.math.log

class RestaurantLocalDataSource(private val db: RoomDB) : RestaurantDataSource {

    override suspend fun getRestaurantsList(): RestaurantsObject {
        val objList = db.restaurantDao().allRestaurants()
        return RestaurantsObject(objList)
    }

    override fun addToFavorite(restaurant: Restaurant) {
        db.restaurantDao().updateRestaurantState(restaurant)
    }

    override fun addAllRestaurants(list: RestaurantsObject) {
        list.restaurants.forEach {
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
        val res = db.restaurantDao().allRestaurants()
            .filter { it.status == RestaurantOrderState.OPEN.status }
        return RestaurantsObject(res)
    }

    override fun getOrderAheadRestaurants(): RestaurantsObject {
        val res = db.restaurantDao().allRestaurants()
            .filter { it.status == RestaurantOrderState.AHEAD.status }
        return RestaurantsObject(res)
    }

    override fun sortRestaurantsBy(
        restaurants: RestaurantsObject,
        sortType: String
    ): RestaurantsObject {

        val favorites = restaurants.restaurants.filter { it.favorite == 1 } as ArrayList<Restaurant>
        val favoritesLessList = restaurants.restaurants.filter { it.favorite == 0 } as ArrayList<Restaurant>
        val openedRestaurants = favoritesLessList.filter { it.status == RestaurantOrderState.OPEN.status } as ArrayList<Restaurant>
        val closedRestaurants = favoritesLessList.filter { it.status == RestaurantOrderState.CLOSED.status } as ArrayList<Restaurant>
        val aheadRestaurants = favoritesLessList.filter { it.status == RestaurantOrderState.AHEAD.status } as ArrayList<Restaurant>

        when (sortType) {
            SortValues.BestMatch.sortKey -> {
                favorites.sortWith(compareBy { it.sortingValues?.bestMatch })
                favoritesLessList.sortWith(compareBy { it.sortingValues?.bestMatch })
                openedRestaurants.sortWith(compareBy { it.sortingValues?.bestMatch })
                closedRestaurants.sortWith(compareBy { it.sortingValues?.bestMatch })
                aheadRestaurants.sortWith(compareBy { it.sortingValues?.bestMatch })
            }
            SortValues.NEWEST.sortKey -> {
                favorites.sortWith(compareBy { it.sortingValues?.newest })
                favoritesLessList.sortWith(compareBy { it.sortingValues?.newest })
                openedRestaurants.sortWith(compareBy { it.sortingValues?.newest })
                closedRestaurants.sortWith(compareBy { it.sortingValues?.newest })
                aheadRestaurants.sortWith(compareBy { it.sortingValues?.newest })
            }
            SortValues.RatingAverage.sortKey -> {
                favorites.sortWith(compareBy { it.sortingValues?.ratingAverage })
                favoritesLessList.sortWith(compareBy { it.sortingValues?.ratingAverage })
                openedRestaurants.sortWith(compareBy { it.sortingValues?.ratingAverage })
                closedRestaurants.sortWith(compareBy { it.sortingValues?.ratingAverage })
                aheadRestaurants.sortWith(compareBy { it.sortingValues?.ratingAverage })
            }
            SortValues.Distance.sortKey -> {
                favorites.sortWith(compareBy { it.sortingValues?.distance })
                favoritesLessList.sortWith(compareBy { it.sortingValues?.distance })
                openedRestaurants.sortWith(compareBy { it.sortingValues?.distance })
                closedRestaurants.sortWith(compareBy { it.sortingValues?.distance })
                aheadRestaurants.sortWith(compareBy { it.sortingValues?.distance })
            }
            SortValues.POPULARITY.sortKey -> {
                favorites.sortWith(compareBy { it.sortingValues?.popularity })
                favoritesLessList.sortWith(compareBy { it.sortingValues?.popularity })
                openedRestaurants.sortWith(compareBy { it.sortingValues?.popularity })
                closedRestaurants.sortWith(compareBy { it.sortingValues?.popularity })
                aheadRestaurants.sortWith(compareBy { it.sortingValues?.popularity })
            }
            SortValues.AverageProductPrice.sortKey -> {
                favorites.sortWith(compareBy { it.sortingValues?.averageProductPrice })
                favoritesLessList.sortWith(compareBy { it.sortingValues?.averageProductPrice })
                openedRestaurants.sortWith(compareBy { it.sortingValues?.averageProductPrice })
                closedRestaurants.sortWith(compareBy { it.sortingValues?.averageProductPrice })
                aheadRestaurants.sortWith(compareBy { it.sortingValues?.averageProductPrice })
            }
            SortValues.DeliveryCosts.sortKey -> {
                favorites.sortWith(compareBy { it.sortingValues?.deliveryCosts })
                favoritesLessList.sortWith(compareBy { it.sortingValues?.deliveryCosts })
                openedRestaurants.sortWith(compareBy { it.sortingValues?.deliveryCosts })
                closedRestaurants.sortWith(compareBy { it.sortingValues?.deliveryCosts })
                aheadRestaurants.sortWith(compareBy { it.sortingValues?.deliveryCosts })
            }
            SortValues.MinCost.sortKey -> {
                favorites.sortWith(compareBy { it.sortingValues?.minCost })
                favoritesLessList.sortWith(compareBy { it.sortingValues?.minCost })
                openedRestaurants.sortWith(compareBy { it.sortingValues?.minCost })
                closedRestaurants.sortWith(compareBy { it.sortingValues?.minCost })
                aheadRestaurants.sortWith(compareBy { it.sortingValues?.minCost })
            }
            else -> {}
        }

        val finalList = favorites + openedRestaurants + aheadRestaurants + closedRestaurants
        return RestaurantsObject(finalList)
    }


}