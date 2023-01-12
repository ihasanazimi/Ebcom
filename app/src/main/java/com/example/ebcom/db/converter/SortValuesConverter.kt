package com.example.ebcom.db.converter

import androidx.room.TypeConverter
import com.example.ebcom.model.SortingValues
import com.google.gson.Gson

class SortValuesConverter {

    @TypeConverter
    fun fromSortValuesToJson(sortValues: SortingValues): String {
        return Gson().toJson(sortValues)
    }

    @TypeConverter
    fun fromJsonToSortValues(sortValuesString: String): SortingValues {
        return Gson().fromJson(sortValuesString , SortingValues::class.java)
    }

}