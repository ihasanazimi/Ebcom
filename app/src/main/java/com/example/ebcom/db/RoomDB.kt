package com.example.ebcom.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ebcom.db.converter.RestaurantConverters
import com.example.ebcom.db.converter.RestaurantsObjectConverter
import com.example.ebcom.db.converter.SortValuesConverter
import com.example.ebcom.db.dao.RestaurantDao
import com.example.ebcom.model.Restaurant
import com.example.ebcom.model.RestaurantsObject
import com.example.ebcom.model.SortingValues

@Database(entities = [RestaurantsObject::class ,Restaurant::class , SortingValues::class , ], version =1 , exportSchema = false)
@TypeConverters( RestaurantsObjectConverter::class,RestaurantConverters::class,SortValuesConverter::class ,)
abstract class RoomDB : RoomDatabase() {

    // Dao`s
    abstract fun restaurantDao(): RestaurantDao

    companion object {
        @Volatile //access just one there on main thread! thread safe..
        var database: RoomDB? = null
        // singleTon design pattern
        fun getDataBase(context: Context): RoomDB {
            val tempInstance = database
            if (database != null) return tempInstance as RoomDB
            //synchronized  -->  means -->  access just one there on main thread!
            synchronized(this) {
                val instance = Room.databaseBuilder(context, RoomDB::class.java, "database")
                    .fallbackToDestructiveMigration().allowMainThreadQueries().build()
                database = instance
                return instance
            }
        }
    }
}