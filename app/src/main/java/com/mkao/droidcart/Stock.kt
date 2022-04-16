package com.mkao.droidcart

import android.app.Application
import com.mkao.droidcart.database.Item
import com.mkao.droidcart.database.ItemRoomDB

class Stock: Application(){
    //by implementing lazy makes only the database created only when needed
    // and not when application starts
    val database : ItemRoomDB by lazy { ItemRoomDB.getDatabase(this)}
}