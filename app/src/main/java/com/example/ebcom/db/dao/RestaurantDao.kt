package com.example.ebcom.db.dao

import androidx.room.*
import com.example.ebcom.model.Restaurant
import com.example.ebcom.model.RestaurantsObject

@Dao
interface RestaurantDao {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insertRestaurant(restaurant: Restaurant) : Long

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insertRestaurantsObj(restaurant: RestaurantsObject) : Long

    @Update
    fun updateRestaurantState(restaurant: Restaurant)

    @Query("select * from Restaurant;")
    fun allRestaurants() : List<Restaurant>

    @Query("select * from Restaurant where favorite = 1;")
    fun getAllFavoritesRestaurants() : List<Restaurant>

    @Delete
    fun deleteRestaurant(restaurant: Restaurant) : Int

}