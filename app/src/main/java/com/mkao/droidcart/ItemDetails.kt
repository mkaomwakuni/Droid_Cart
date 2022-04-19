package com.mkao.droidcart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mkao.droidcart.database.Item
import com.mkao.droidcart.databinding.ItemDetailsBinding

class ItemDetails : Fragment() {
    private val navigationArgs :ItemDetails by navigationArgs()
    lateinit var item: Item

    private val viewModel: StockViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as StockApplication).database.itemDao()
        )
    }

    private var _binding : ItemDetailsBinding? = null
    private val  binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        _binding = ItemDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }


}