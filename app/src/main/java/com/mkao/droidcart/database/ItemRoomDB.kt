package com.mkao.droidcart.database

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.security.AccessControlContext

//Database class with a singleton INSTANCE Object
@Database(entities = [item::class],version = 1,exportSchema =false )

abstract class ItemRoomDB : RoomDatabase(){
    abstract fun itemDao() :ItemDao

    companion object{
        @Volatile
        private var INSTANCE:ItemRoomDB?=null

        fun getDatabase(context: Context):ItemRoomDB {
            //if INSTANCE is not null return it
            //if its ,create the database
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                context.applicationContext,
                ItemRoomDB::class.java,
                "Stock_database"
            )
            //wipe and rebuilds instead of migrating if not migration object
            //Currently not implemented
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            //returns instance
            instance
        }
    }
  }
}