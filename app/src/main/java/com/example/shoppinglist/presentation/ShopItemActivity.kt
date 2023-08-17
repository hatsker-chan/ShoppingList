package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityShopItemBinding
import com.example.shoppinglist.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopItemBinding
    private lateinit var viewModel: ShopItemViewModel
    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()

        launchRightMode()
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_container, fragment)
            .commit()
    }

    //
//    private fun observeViewModel() {
//        viewModel.finishActivity.observe(this) {
//            val intent = MainActivity.newIntent(this)
//            startActivity(intent)
//        }
//
//        viewModel.errorInputCount.observe(this) {
//            if (it) {
//                binding.tilCount.error = "Incorrect count input"
//            } else {
//                binding.tilCount.error = null
//            }
//
//        }
//        viewModel.errorInputName.observe(this) {
//            if (it) {
//                binding.tilName.error = "Incorrect name input"
//            } else {
//                binding.tilName.error = null
//            }
//        }
//    }
//
//    private fun setupTextChangedListeners() {
//        binding.etName.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetErrorInputName()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//        })
//
//        binding.etCount.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetErrorInputCount()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//
//        })
//    }
//
//
//    private fun launchEditMode() {
//        viewModel.getShopItem(shopItemId)
//        viewModel.shopItem.observe(this) {
//            binding.etName.setText(it.name)
//            binding.etCount.setText(it.count.toString())
//        }
//        binding.buttonSave.setOnClickListener {
//            val name = binding.etName.text.toString().trim()
//            val count = binding.etCount.text.toString().trim()
//            viewModel.editShopItem(name, count)
//        }
//
//    }
//
//    private fun launchAddMode() {
//        binding.buttonSave.setOnClickListener {
//            val name = binding.etName.text.toString().trim()
//            val count = binding.etCount.text.toString().trim()
//            viewModel.addShopItem(name, count)
//        }
//    }
//
    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_ITEM_ID, ShopItem.UNDEFINED_ID)
        }

    }

    companion object {
        private const val EXTRA_ITEM_ID = "id"
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = ""

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