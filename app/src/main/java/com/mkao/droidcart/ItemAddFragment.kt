package com.mkao.droidcart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mkao.droidcart.database.Item
import com.mkao.droidcart.databinding.ItemAddFragmentBinding

//The fragment to add and update an item in the inventory db
class ItemAddFragment : Fragment() {
    //use of by activityViewModels() ' a property in kotlin from fragment ktx
    //to share the ViewModel across fragments
    private val viewModel:StockViewModel by activityViewModels {
        InventoryViewModelFactory((activity?.application as StockApplication)
            .database.itemDao())
    }

    private val navigationArgs:ItemDetails by navArgs()
    lateinit var item: Item

    // Binding object instance corresponding to the fragment_add_item.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment
    private var _binding:ItemAddFragmentBinding? = null
    private val  binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    _binding= ItemAddFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }
    //returns true if EditTexts are not Empty
    private fun isEntryValid():Boolean{
        return viewModel.isEntryValid(
            binding.itemName.text.toString(),
            binding.itemPrice.text.toString(),
            binding.itemQuantity.text.toString(),
        )
    }
    //Binds views with the passed Item Information
    private fun bind(item: Item){
        val price = "%.2f".format(item.itemPrice)
        binding.apply {
            itemName.setText(item.itemName,TextView.BufferType.SPANNABLE)
            itemPrice.setText(price,TextView.BufferType.SPANNABLE)
            itemQuantity.setText(item.itemQuantity.toString(),TextView.BufferType.SPANNABLE)
            saveBtn.setOnClickListener { updateItem() }
        }
    }

    private fun updateItem() {
        TODO("Not yet implemented")
    }
}