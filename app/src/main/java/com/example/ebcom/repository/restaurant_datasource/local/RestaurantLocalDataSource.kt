package com.example.ebcom.repository.restaurant_datasource.local

import com.example.ebcom.db.RoomDB
import com.example.ebcom.model.Restaurant
import com.example.ebcom.model.RestaurantsObject
import com.example.ebcom.model.seald.SealdRestaurantStatus
import com.example.ebcom.model.seald.SealdSortValues
import com.example.ebcom.repository.restaurant_datasource.RestaurantDataSource

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
            .filter { it.status == SealdRestaurantStatus.OPEN.status }
        return RestaurantsObject(res)
    }

    override fun getOrderAheadRestaurants(): RestaurantsObject {
        val res = db.restaurantDao().allRestaurants()
            .filter { it.status == SealdRestaurantStatus.AHEAD.status }
        return RestaurantsObject(res)
    }

    override fun sortRestaurantsBy(
        restaurants: RestaurantsObject,
        sortType: String
    ): RestaurantsObject {

        var favorites = restaurants.restaurants.filter { it.favorite == 1 } as ArrayList<Restaurant>
        val favoritesLessList = restaurants.restaurants.filter { it.favorite == 0 } as ArrayList<Restaurant>
        val openedRestaurants = favoritesLessList.filter { it.status == SealdRestaurantStatus.OPEN.status } as ArrayList<Restaurant>
        val closedRestaurants = favoritesLessList.filter { it.status == SealdRestaurantStatus.CLOSED.status } as ArrayList<Restaurant>
        val aheadRestaurants = favoritesLessList.filter { it.status == SealdRestaurantStatus.AHEAD.status } as ArrayList<Restaurant>

        when (sortType) {
            SealdSortValues.BestMatch.sortKey -> {
                favorites.sortWith(compareBy { it.sortingValues?.bestMatch })
                val o = favorites.filter { it.status ==  SealdRestaurantStatus.OPEN.status}
                val oa = favorites.filter { it.status ==  SealdRestaurantStatus.AHEAD.status}
                val c = favorites.filter { it.status ==  SealdRestaurantStatus.CLOSED.status}
                favorites.clear()
                favorites = (o + oa + c) as ArrayList<Restaurant>
                favoritesLessList.sortWith(compareBy { it.sortingValues?.bestMatch })
                openedRestaurants.sortWith(compareBy { it.sortingValues?.bestMatch })
                closedRestaurants.sortWith(compareBy { it.sortingValues?.bestMatch })
                aheadRestaurants.sortWith(compareBy { it.sortingValues?.bestMatch })
            }
            SealdSortValues.NEWEST.sortKey -> {
                favorites.sortWith(compareBy { it.sortingValues?.newest })
                val o = favorites.filter { it.status ==  SealdRestaurantStatus.OPEN.status}
                val oa = favorites.filter { it.status ==  SealdRestaurantStatus.AHEAD.status}
                val c = favorites.filter { it.status ==  SealdRestaurantStatus.CLOSED.status}
                favorites.clear()
                favorites = (o + oa + c) as ArrayList<Restaurant>
                favoritesLessList.sortWith(compareBy { it.sortingValues?.newest })
                openedRestaurants.sortWith(compareBy { it.sortingValues?.newest })
                closedRestaurants.sortWith(compareBy { it.sortingValues?.newest })
                aheadRestaurants.sortWith(compareBy { it.sortingValues?.newest })
            }
            SealdSortValues.RatingAverage.sortKey -> {
                favorites.sortWith(compareBy { it.sortingValues?.ratingAverage })
                val o = favorites.filter { it.status ==  SealdRestaurantStatus.OPEN.status}
                val oa = favorites.filter { it.status ==  SealdRestaurantStatus.AHEAD.status}
                val c = favorites.filter { it.status ==  SealdRestaurantStatus.CLOSED.status}
                favorites.clear()
                favorites = (o + oa + c) as ArrayList<Restaurant>
                favoritesLessList.sortWith(compareBy { it.sortingValues?.ratingAverage })
                openedRestaurants.sortWith(compareBy { it.sortingValues?.ratingAverage })
                closedRestaurants.sortWith(compareBy { it.sortingValues?.ratingAverage })
                aheadRestaurants.sortWith(compareBy { it.sortingValues?.ratingAverage })
            }
            SealdSortValues.Distance.sortKey -> {
                favorites.sortWith(compareBy { it.sortingValues?.distance })
                val o = favorites.filter { it.status ==  SealdRestaurantStatus.OPEN.status}
                val oa = favorites.filter { it.status ==  SealdRestaurantStatus.AHEAD.status}
                val c = favorites.filter { it.status ==  SealdRestaurantStatus.CLOSED.status}
                favorites.clear()
                favorites = (o + oa + c) as ArrayList<Restaurant>
                favoritesLessList.sortWith(compareBy { it.sortingValues?.distance })
                openedRestaurants.sortWith(compareBy { it.sortingValues?.distance })
                closedRestaurants.sortWith(compareBy { it.sortingValues?.distance })
                aheadRestaurants.sortWith(compareBy { it.sortingValues?.distance })
            }
            SealdSortValues.POPULARITY.sortKey -> {
                favorites.sortWith(compareBy { it.sortingValues?.popularity })
                val o = favorites.filter { it.status ==  SealdRestaurantStatus.OPEN.status}
                val oa = favorites.filter { it.status ==  SealdRestaurantStatus.AHEAD.status}
                val c = favorites.filter { it.status ==  SealdRestaurantStatus.CLOSED.status}
                favorites.clear()
                favorites = (o + oa + c) as ArrayList<Restaurant>
                favoritesLessList.sortWith(compareBy { it.sortingValues?.popularity })
                openedRestaurants.sortWith(compareBy { it.sortingValues?.popularity })
                closedRestaurants.sortWith(compareBy { it.sortingValues?.popularity })
                aheadRestaurants.sortWith(compareBy { it.sortingValues?.popularity })
            }
            SealdSortValues.AverageProductPrice.sortKey -> {
                favorites.sortWith(compareBy { it.sortingValues?.averageProductPrice })
                val o = favorites.filter { it.status ==  SealdRestaurantStatus.OPEN.status}
                val oa = favorites.filter { it.status ==  SealdRestaurantStatus.AHEAD.status}
                val c = favorites.filter { it.status ==  SealdRestaurantStatus.CLOSED.status}
                favorites.clear()
                favorites = (o + oa + c) as ArrayList<Restaurant>
                favoritesLessList.sortWith(compareBy { it.sortingValues?.averageProductPrice })
                openedRestaurants.sortWith(compareBy { it.sortingValues?.averageProductPrice })
                closedRestaurants.sortWith(compareBy { it.sortingValues?.averageProductPrice })
                aheadRestaurants.sortWith(compareBy { it.sortingValues?.averageProductPrice })
            }
            SealdSortValues.DeliveryCosts.sortKey -> {
                favorites.sortWith(compareBy { it.sortingValues?.deliveryCosts })
                val o = favorites.filter { it.status ==  SealdRestaurantStatus.OPEN.status}
                val oa = favorites.filter { it.status ==  SealdRestaurantStatus.AHEAD.status}
                val c = favorites.filter { it.status ==  SealdRestaurantStatus.CLOSED.status}
                favorites.clear()
                favorites = (o + oa + c) as ArrayList<Restaurant>
                favoritesLessList.sortWith(compareBy { it.sortingValues?.deliveryCosts })
                openedRestaurants.sortWith(compareBy { it.sortingValues?.deliveryCosts })
                closedRestaurants.sortWith(compareBy { it.sortingValues?.deliveryCosts })
                aheadRestaurants.sortWith(compareBy { it.sortingValues?.deliveryCosts })
            }
            SealdSortValues.MinCost.sortKey -> {
                favorites.sortWith(compareBy { it.sortingValues?.minCost })
                val o = favorites.filter { it.status ==  SealdRestaurantStatus.OPEN.status}
                val oa = favorites.filter { it.status ==  SealdRestaurantStatus.AHEAD.status}
                val c = favorites.filter { it.status ==  SealdRestaurantStatus.CLOSED.status}
                favorites.clear()
                favorites = (o + oa + c) as ArrayList<Restaurant>
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