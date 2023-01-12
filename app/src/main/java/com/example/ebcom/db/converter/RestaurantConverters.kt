package com.example.ebcom.db.converter

import androidx.room.TypeConverter
import com.example.ebcom.model.Restaurant
import com.google.gson.Gson

class RestaurantConverters {

    @TypeConverter
    fun fromRestaurantToJson(restaurant: Restaurant): String {
        return Gson().toJson(restaurant)
    }

    @TypeConverter
    fun fromJsonToRestaurant(restaurantString: String): Restaurant {
        return Gson().fromJson(restaurantString , Restaurant::class.java)
    }


}