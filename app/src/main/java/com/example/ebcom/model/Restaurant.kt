package com.example.ebcom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Restaurant(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Long ,
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "status")
    val status: String = "",
    @ColumnInfo(name = "cover")
    val cover: String = "",
    @ColumnInfo(name = "sortingValues")
    val sortingValues: SortingValues ? = null,
    @ColumnInfo(name = "favorite")
    var favorite : Int = 0
) : java.io.Serializable {

    fun statusByParenteral() :String = "($status)"

}