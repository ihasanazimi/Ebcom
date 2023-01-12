package com.example.ebcom.db.converter

import androidx.room.TypeConverter
import com.example.ebcom.model.Restaurant
import com.example.ebcom.model.RestaurantsObject
import com.google.gson.Gson

class RestaurantsObjectConverter {

    @TypeConverter
    fun fromRestaurantsObjectToJson(restaurants: RestaurantsObject): String {
        return Gson().toJson(restaurants)
    }

    @TypeConverter
    fun fromJsonToRestaurantsObject(restaurantsString: String): RestaurantsObject {
        return Gson().fromJson(restaurantsString  , RestaurantsObject::class.java)
    }


    @TypeConverter
    fun fromRestaurantsToJson(restaurants: List<Restaurant>): String {
        return Gson().toJson(restaurants)
    }

    @TypeConverter
    fun fromJsonToRestaurants(restaurantsString: String): List<Restaurant> {
        return Gson().fromJson(restaurantsString  , Array<Restaurant>::class.java).toCollection(ArrayList())
    }

}