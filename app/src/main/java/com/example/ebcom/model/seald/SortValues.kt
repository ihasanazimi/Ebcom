package com.example.ebcom.model.seald

sealed class SortValues (var sortKey : String){
    object BestMatch  : SortValues("bestMatch")
    object NEWEST : SortValues("newest")
    object RatingAverage : SortValues("ratingAverage")
    object Distance : SortValues("distance")
    object POPULARITY : SortValues("popularity")
    object AverageProductPrice : SortValues("averageProductPrice")
    object DeliveryCosts : SortValues("deliveryCosts")
    object MinCost : SortValues("minCost")
}