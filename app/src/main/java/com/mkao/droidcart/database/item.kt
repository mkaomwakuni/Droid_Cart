package com.mkao.droidcart.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.RowId

//Entity class represents a single row in a database

@Entity
 data class item(@PrimaryKey(autoGenerate = true)
val id: Int = 0),
@ColumnInfo(name = "name")
val itemName:String,
@ColumnInfo(name = "price")
val itemPrice:Double,
        @ColumnInfo(name = "quantity")
val itemQuantity:Int,)

//Returning the
