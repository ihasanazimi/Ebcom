package com.example.ebcom.model.seald

sealed class SealdSortValues (var sortKey : String){
    object BestMatch  : SealdSortValues("bestMatch")
    object NEWEST : SealdSortValues("newest")
    object RatingAverage : SealdSortValues("ratingAverage")
    object Distance : SealdSortValues("distance")
    object POPULARITY : SealdSortValues("popularity")
    object AverageProductPrice : SealdSortValues("averageProductPrice")
    object DeliveryCosts : SealdSortValues("deliveryCosts")
    object MinCost : SealdSortValues("minCost")
}