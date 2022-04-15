package com.mkao.droidcart.database

import androidx.room.*
import java.util.concurrent.Flow

//Database access object to have access to the App Database
@Dao
interface ItemDao {
    @Query("SELECT * from item ORDER BY name ASC")
    fun getItems():Flow<List<Item>>

    @Query("SELECT * from item WHERE id = :id")
    fun getItem(id:Int):Flow<Item>

    //Conflict strategy as IGNORE, when the use user tries to add an existing item into
    //the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)
}