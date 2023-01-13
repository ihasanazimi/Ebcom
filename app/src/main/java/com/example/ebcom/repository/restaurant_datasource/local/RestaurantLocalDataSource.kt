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
        val favoritesLessList =
            restaurants.restaurants.filter { it.favorite == 0 } as ArrayList<Restaurant>
        val openedRestaurants =
            favoritesLessList.filter { it.status == RestaurantOrderState.OPEN.status } as ArrayList<Restaurant>
        val closedRestaurants =
            favoritesLessList.filter { it.status == RestaurantOrderState.CLOSED.status } as ArrayList<Restaurant>
        val aheadRestaurants =
            favoritesLessList.filter { it.status == RestaurantOrderState.AHEAD.status } as ArrayList<Restaurant>
        val numbers = arrayOf(2, 8, 7, 9, 4, 3, 1, 10)

        when (sortType) {
            SortValues.BestMatch.sortKey -> {
                favorites.sortBy { it.sortingValues?.bestMatch }
                favoritesLessList.sortWith(compareBy { it.sortingValues?.bestMatch })
                openedRestaurants.sortWith(compareBy { it.sortingValues?.bestMatch })
                closedRestaurants.sortWith(compareBy { it.sortingValues?.bestMatch })
                aheadRestaurants.sortWith(compareBy { it.sortingValues?.bestMatch })
            }
            SortValues.NEWEST.sortKey -> {
                favorites.sortBy { it.sortingValues?.newest }
                favoritesLessList.sortBy { it.sortingValues?.newest }
                openedRestaurants.sortBy { it.sortingValues?.newest }
                closedRestaurants.sortBy { it.sortingValues?.newest }
                aheadRestaurants.sortBy { it.sortingValues?.newest }
            }
            SortValues.RatingAverage.sortKey -> {
                favorites.sortBy { it.sortingValues?.ratingAverage }
                favoritesLessList.sortBy { it.sortingValues?.ratingAverage }
                openedRestaurants.sortBy { it.sortingValues?.ratingAverage }
                closedRestaurants.sortBy { it.sortingValues?.ratingAverage }
                aheadRestaurants.sortBy { it.sortingValues?.ratingAverage }
            }
            SortValues.Distance.sortKey -> {
                favorites.sortBy { it.sortingValues?.distance }
                favoritesLessList.sortBy { it.sortingValues?.distance }
                openedRestaurants.sortBy { it.sortingValues?.distance }
                closedRestaurants.sortBy { it.sortingValues?.distance }
                aheadRestaurants.sortBy { it.sortingValues?.distance }
            }
            SortValues.POPULARITY.sortKey -> {
                favorites.sortBy { it.sortingValues?.popularity }
                favoritesLessList.sortBy { it.sortingValues?.popularity }
                openedRestaurants.sortBy { it.sortingValues?.popularity }
                closedRestaurants.sortBy { it.sortingValues?.popularity }
                aheadRestaurants.sortBy { it.sortingValues?.popularity }
            }
            SortValues.AverageProductPrice.sortKey -> {
                favorites.sortBy { it.sortingValues?.averageProductPrice }
                favoritesLessList.sortBy { it.sortingValues?.averageProductPrice }
                openedRestaurants.sortBy { it.sortingValues?.averageProductPrice }
                closedRestaurants.sortBy { it.sortingValues?.averageProductPrice }
                aheadRestaurants.sortBy { it.sortingValues?.averageProductPrice }
            }
            SortValues.DeliveryCosts.sortKey -> {
                favorites.sortWith(compareBy { it.sortingValues?.deliveryCosts })
                favoritesLessList.sortWith(compareBy { it.sortingValues?.deliveryCosts })
                openedRestaurants.sortWith(compareBy { it.sortingValues?.deliveryCosts })
                closedRestaurants.sortWith(compareBy { it.sortingValues?.deliveryCosts })
                aheadRestaurants.sortWith(compareBy { it.sortingValues?.deliveryCosts })
                numbers.sortBy { it }
            }
            SortValues.MinCost.sortKey -> {
                favorites.sortBy { it.sortingValues?.minCost }
                favoritesLessList.sortBy { it.sortingValues?.minCost }
                openedRestaurants.sortBy { it.sortingValues?.minCost }
                closedRestaurants.sortBy { it.sortingValues?.minCost }
                aheadRestaurants.sortBy { it.sortingValues?.minCost }
            }
            else -> {}
        }

        val finalList = favorites + openedRestaurants + aheadRestaurants + closedRestaurants
        when (sortType) {
            SortValues.BestMatch.sortKey -> {
                finalList.forEach { rest ->
                    Log.i(
                        "hsn",
                        "sortRestaurantsBy bestMatch : " + rest.sortingValues?.bestMatch
                    )
                }
            }
            SortValues.NEWEST.sortKey -> {
                finalList.forEach { rest ->
                    Log.i(
                        "hsn",
                        "sortRestaurantsBy newest : " + rest.sortingValues?.newest
                    )
                }
            }
            SortValues.RatingAverage.sortKey -> {
                finalList.forEach { rest ->
                    Log.i(
                        "hsn",
                        "sortRestaurantsBy ratingAverage : " + rest.sortingValues?.ratingAverage
                    )
                }
            }
            SortValues.Distance.sortKey -> {
                finalList.forEach { rest ->
                    Log.i(
                        "hsn",
                        "sortRestaurantsBy distance : " + rest.sortingValues?.distance
                    )
                }
            }
            SortValues.POPULARITY.sortKey -> {
                finalList.forEach { rest ->
                    Log.i(
                        "hsn",
                        "sortRestaurantsBy popularity : " + rest.sortingValues?.popularity
                    )
                }
            }
            SortValues.AverageProductPrice.sortKey -> {
                finalList.forEach { rest ->
                    Log.i(
                        "hsn",
                        "sortRestaurantsBy averageProductPrice : " + rest.sortingValues?.averageProductPrice
                    )
                }
            }
            SortValues.DeliveryCosts.sortKey -> {
                finalList.forEach { rest ->
                    Log.i(
                        "hsn",
                        "sortRestaurantsBy deliveryCosts : " + rest.sortingValues?.deliveryCosts
                    )
                }
                numbers.forEach { Log.i("hsn", it.toString()) }
            }
            SortValues.MinCost.sortKey -> {
                finalList.forEach { rest ->
                    Log.i(
                        "hsn",
                        "sortRestaurantsBy minCost : " + rest.sortingValues?.minCost
                    )
                }
            }
        }
        return RestaurantsObject(finalList)
    }


}