package com.mkao.droidcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mkao.droidcart.database.Item
import com.mkao.droidcart.database.ItemDao
import kotlinx.coroutines.launch
import java.sql.RowId
import kotlin.concurrent.thread

//This viewModel keeps the reference  and updates the list of items
class StockViewModel(private val itemDao: ItemDao) :ViewModel() {
    //cache the items in the database using the livedata
    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    //return true if stock is available for sale otherwise return false
    fun isStockAvailable(item: Item): Boolean {
        return (item.itemQuantity > 0)
    }

    //updates on existing products on the database
    fun updateItem(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ) {
        val updatedItem = getUpdatedItemEntry(itemId, itemName, itemPrice, itemCount)
        updateItem(updatedItem)

    }

    //launching a new coroutine to update the item in non- blocking manner
    private fun updateItem(item: Item) {
        viewModelScope.launch { itemDao.update(item) }
    }

    //decrease a unit by one when sold and notifies the database
    fun sellItem(item: Item) {
        if (item.itemQuantity > 0) {
            //decrease by 1
            val newItem = item.copy(itemQuantity = item.itemQuantity - 1)
            updateItem(newItem)
        }
    }
    //insert a new item into db
    fun addNewItem(itemName: String,itemPrice: String,itemCount: String){
        val newItem = getNewItemEntry(itemName,itemPrice,itemCount)
        insertItem(newItem)
    }

    //Launching a  coroutine to insert an item in non blocking manner
    fun insertItem(item: Item) {
        viewModelScope.launch { itemDao.insert(item) }
    }

    //launching a new thread when an item has been deleted
    fun deleteItem(item: Item) {
        viewModelScope.launch { itemDao.delete(item) }
    }

    //retrieve item from the database
    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getItem(id).asLiveData()
    }

    //Returns true if the EditTexts are not empty
    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }

    //returns instance of item entity class with item info entered by user
    //which will be used to enter an entry into the database
    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String): Item {
        return Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            itemQuantity = itemCount.toInt()
        )
    }

    //its called when one wants to update an existing item in DB
    //returns instance of Item entity class with info updated by the user
    private fun getUpdatedItemEntry(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ): Item {
        return Item(
            id =itemId,
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            itemQuantity =itemCount.toInt()
        )
     }

}
//factory class to instantiate ViewModel
class InventoryViewModelFactory (private val itemDao: ItemDao): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StockViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return StockViewModel(itemDao) as T
        }
        throw IllegalArgumentException ("Unknown ViewModel Class")

    }
}
