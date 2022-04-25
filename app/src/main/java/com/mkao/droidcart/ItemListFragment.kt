package com.mkao.droidcart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mkao.droidcart.databinding.ItemListFragmentBinding

class ItemListFragment : Fragment() {
    private val viewModel: StockViewModel by activityViewModels{
        InventoryViewModelFactory(
            (activity?.application as StockApplication).database.itemDao()
        )
    }


    private var _binding :ItemListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ItemListFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ItemListAdapter {
            val action = ItemListFragmentDirections.itemListFragment_to_item_add_fragment{
                this.findNavController().navigate(action)
            }
        binding.recyclerView.layoutManager = LinearLayout(this.context)
        binding.recyclerView.adapter= adapter

        //Attach an observer on allItems list to update the UI automatically
        //changes
        viewModel.allItems.observe(this.viewLifecycleOwner){ items->
            items.let {
                adapter.submitList(it)
            }
        }
        binding.floatingbtn.setOnClickListener {
            val action = ItemListFragmentDirections.action_itemDetailFragment_to_addItemFragment
            (getString(R.string.add_fragment_title)
                    )
                    this.findNavController().navigate(action)
        }
      }
    }
