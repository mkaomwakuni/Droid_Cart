package com.mkao.droidcart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListAdapter
import com.mkao.droidcart.database.Item

//This adapter implements for recyclerView
class itemListAdapter(private val onItemClicked:(Item)->Unit) :
ListAdapter<Item,itemListAdapter.ItemViewHolder>(DiffCallback) {



    object DiffCallback {

    }

    class ItemViewHolder {

    }

}


