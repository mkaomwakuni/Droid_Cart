package com.mkao.droidcart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mkao.droidcart.database.Item
import com.mkao.droidcart.database.getFormattedPrice
import com.mkao.droidcart.databinding.ItemDetailsBinding

class ItemDetails : Fragment() {
    private val navigationArgs :ItemDetails by navArgs()
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
        _binding = ItemDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }
    //Binds views with the passed in item data
    private fun bind(item: Item){
        binding.apply {
            itemName.text = item.itemName
            itemPrice.text = item.getFormattedPrice()
            itemQuantity.text = item.itemQuantity.toString()
            sellItem.isEnabled = viewModel.isStockAvailable(item)
            sellItem.setOnClickListener { viewModel.sellItem(item) }
            deleteItem.setOnClickListener { showConfirmationDialog() }
            editFlotBtn.setOnClickListener { editItem() }
        }
    }

    //edit item at edit screen
    private fun editItem() {
        val  action = ItemDetailDirections.action_item_details_to_item_add_fragment
        (getString(R.string.edit_item),item.id)
        this.findNavController().navigate(action)
    }

    private fun showConfirmationDialog() {
        /**
         * Displays an alert dialog to get the user's confirmation before deleting the item.
         */
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)){_,_ ->}
            .setPositiveButton(getString(R.string.yes)) {_,_ ->
                deleteItem()
            }
            .show()
    }

    //deletes a current item and navigates to the list fragment

    private fun deleteItem() {
     viewModel.deleteItem(item)
        findNavController().navigateUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        // Retrieve the item details using the itemId.
        // Attach an observer on the data (instead of polling for changes) and only update the
        // the UI when the data actually changes.
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) {selectedItem ->
            item = selectedItem
            bind(item)
        }
    }
    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}