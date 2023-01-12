package com.example.ebcom.ui.fragments.restaurants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ebcom.model.Restaurant
import com.example.ebcom.model.RestaurantsObject
import com.example.ebcom.repository.RestaurantRepositoryImpl
import com.example.ebcom.utility.base.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class RestaurantsVM(private val repository : RestaurantRepositoryImpl) : BaseViewModel() {

    private val _restaurants = MutableLiveData<RestaurantsObject>()
    val restaurants: LiveData<RestaurantsObject> = _restaurants
    val isDone = MutableLiveData<Boolean>(false)

    private val handler = CoroutineExceptionHandler { _, exception ->
        errorLiveData.postValue(arrayListOf(exception.message.toString()))
    }

    fun addRestaurantsList(restaurants : RestaurantsObject){
        repository.addAllRestaurants(restaurants)
    }

    fun getAllRestaurants() {
        isDone.value = true
        viewModelScope.launch(handler) {
            val res = repository.getAllRestaurantsList()
            _restaurants.value = res
            isDone.value = false
        }
    }

    fun getSortRestaurantsBy(sortType : String){
        isDone.value = true
        viewModelScope.launch(handler) {
            val res = repository.getAllRestaurantsList()
            val sortedList = repository.sortRestaurantsBy(res,sortType)
            _restaurants.value = sortedList
            isDone.value = false
        }
    }

    fun getSortFavoritesRestaurantsBy(sortType : String){
        isDone.value = true
        val res = repository.getFavoritesListRestaurants()
        val sortedList = repository.sortRestaurantsBy(res,sortType)
        _restaurants.value = sortedList
        isDone.value = false
    }

    fun getOpenedRestaurants(){
        isDone.value = true
        val res = repository.getOpenedRestaurants()
        _restaurants.value = res
        isDone.value = false
    }


    fun getAheadRestaurants(){
        isDone.value = true
        val res = repository.getOrderAheadRestaurants()
        _restaurants.value = res
        isDone.value = false
    }

    fun getFavoritesRestaurants(){
        isDone.value = true
        val res = repository.getFavoritesListRestaurants()
        _restaurants.value = res
        isDone.value = false
    }

    fun addToFavorite(restaurant : Restaurant){
        isDone.value = true
        repository.addToFavorite(restaurant)
        update(restaurant)
        isDone.value = false
    }

     fun updateFavorite(restaurant : Restaurant){
        isDone.value = true
         repository.updateFavorites(restaurant)
         update(restaurant)
         isDone.value = false
    }

    fun deleteFavorite(restaurant : Restaurant){
        isDone.value = true
        repository.deleteFromFavorites(restaurant)
        update(restaurant)
        isDone.value = false
    }

    private fun update(restaurant: Restaurant) {
        val temps = _restaurants.value?.restaurants?.toMutableList()
        val targetTemp = temps?.find { it.name == restaurant.name }
        val targetIndex = temps?.indexOf(targetTemp)
        if (targetIndex != null) temps[targetIndex] = restaurant
    }


}