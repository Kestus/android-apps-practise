package com.kes.app041_kt_shoppinglist.presentation.viewModel

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kes.app041_kt_shoppinglist.domain.model.ShopItem
import com.kes.app041_kt_shoppinglist.domain.useCases.AddShopItemUseCase
import com.kes.app041_kt_shoppinglist.domain.useCases.EditShopItemUseCase
import com.kes.app041_kt_shoppinglist.domain.useCases.GetShopItemUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopItemViewModel @Inject constructor(
    private val getShopItemUseCase: GetShopItemUseCase,
    private val addShopItemUseCase: AddShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase,
    private val contentResolver: ContentResolver,
) : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean> get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean> get() = _errorInputCount

    private var _currentShopItem = MutableLiveData<ShopItem?>()
    val currentShopItem: LiveData<ShopItem?> get() = _currentShopItem

    private var _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> get() = _loading

    private var _finished = MutableLiveData<Unit>()
    val finished: LiveData<Unit> get() = _finished

    fun getShopItem(id: Int) = viewModelScope.launch {
        val item = getShopItemUseCase(id)
        _currentShopItem.value = item
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        if (!inputIsValid(name, count)) {
            return
        }

        val item = ShopItem(name, count)
        launchToFinish {
            addShopItemUseCase(item)
        }
    }

    fun addShopItemProvider(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        if (!inputIsValid(name, count)) {
            return
        }

        // Add through content provider
        launchToFinish {
            ioScope.launch {
                contentResolver.insert(
                    Uri.parse("content://com.kes.app041_kt_shoppinglist/items"),
                    ContentValues().apply {
                        put("id", 0)
                        put("name", name)
                        put("count", count)
                        put("enabled", true)
                    }
                )
            }
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        if (!inputIsValid(name, count)) {
            return
        }

        _currentShopItem.value?.let {
            val item = it.copy(name = name, count = count)
            launchToFinish {
                editShopItemUseCase(item)
            }
        }
    }

    fun editShopItemProvider(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        if (!inputIsValid(name, count)) {
            return
        }

        _currentShopItem.value?.let {
            launchToFinish {
                val updatedItem = it.copy(name = name, count = count)
                ioScope.launch {
                    contentResolver.update(
                        Uri.parse("content://com.kes.app041_kt_shoppinglist/items"),
                        updatedItem.contentValues,
                        null,
                        arrayOf(it.id.toString())
                    )
                }
            }
        }

    }

    private fun launchToFinish(task: suspend () -> Unit) {
        _loading.value = true
        viewModelScope.launch {
            task.invoke()
            _loading.value = false
            finish()
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun inputIsValid(name: String, count: Int): Boolean {
        var valid = true
        if (name.isBlank()) {
            _errorInputName.value = true
            valid = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            valid = false
        }
        return valid
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finish() {
        _finished.value = Unit
    }
}