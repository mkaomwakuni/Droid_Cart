package com.mkao.droidcart.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.RowId
import java.text.NumberFormat

//Entity class represents a single row in a database

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
val id: Int = 0,
@ColumnInfo(name = "name")
val itemName:String,
@ColumnInfo(name = "price")
val itemPrice:Double,
@ColumnInfo(name = "quantity")
val itemQuantity:Int,
)

//Returning the passed price in terms of real world currency
fun Item.getFormattedPrice (): String =
    NumberFormat.getCurrencyInstance().format(itemPrice)
