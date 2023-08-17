package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment(
    private val screenMode: String = MODE_UNKNOWN,
    private val shopItemId: Int = ShopItem.UNDEFINED_ID
) : Fragment() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var button_save: Button
    private lateinit var et_name: EditText
    private lateinit var et_count: EditText
    private lateinit var til_name: TextInputLayout
    private lateinit var til_count: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_save = view.findViewById(R.id.button_save)
        et_name = view.findViewById(R.id.et_name)
        et_count = view.findViewById(R.id.et_count)
        til_name = view.findViewById(R.id.til_name)
        til_count = view.findViewById(R.id.til_count)
        parseParams()

        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]

        launchRightMode()
        setupTextChangedListeners()
        observeViewModel()
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun observeViewModel() {
        viewModel.finishActivity.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }

        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            if (it) {
                til_count.error = "Incorrect count input"
            } else {
                til_count.error = null
            }
        }

        viewModel.errorInputName.observe(viewLifecycleOwner) {
            if (it) {
                til_name.error = "Incorrect name input"
            } else {
                til_name.error = null
            }
        }
    }

    private fun setupTextChangedListeners() {
        et_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        et_count.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }


    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner) {
            et_name.setText(it.name)
            et_count.setText(it.count.toString())
        }
        button_save.setOnClickListener {
            val name = et_name.text.toString().trim()
            val count = et_count.text.toString().trim()
            viewModel.editShopItem(name, count)
        }

    }

    private fun launchAddMode() {
        button_save.setOnClickListener {
            val name = et_name.text.toString().trim()
            val count = et_count.text.toString().trim()
            viewModel.addShopItem(name, count)
        }
    }

    private fun parseParams() {
        if (screenMode != MODE_EDIT && screenMode != MODE_ADD){
            throw RuntimeException("Param screen mode is absent")
        }
        if (screenMode == MODE_EDIT && shopItemId == ShopItem.UNDEFINED_ID) {
           throw RuntimeException("Param shop item id is absent")
        }
    }

    companion object {
        private const val EXTRA_ITEM_ID = "id"
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment{
            return ShopItemFragment(MODE_ADD)
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment{
            return ShopItemFragment(MODE_EDIT, shopItemId)
        }

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEdit(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_ITEM_ID, shopItemId)
            return intent
        }
    }
}