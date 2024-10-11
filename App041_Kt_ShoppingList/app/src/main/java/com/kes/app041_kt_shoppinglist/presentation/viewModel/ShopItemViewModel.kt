package com.kes.app041_kt_shoppinglist.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kes.app041_kt_shoppinglist.data.ShopListRepositoryImpl
import com.kes.app041_kt_shoppinglist.domain.ShopItem
import com.kes.app041_kt_shoppinglist.domain.useCases.AddShopItemUseCase
import com.kes.app041_kt_shoppinglist.domain.useCases.EditShopItemUseCase
import com.kes.app041_kt_shoppinglist.domain.useCases.GetShopItemUseCase
import kotlinx.coroutines.launch

class ShopItemViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

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
        val item = getShopItemUseCase.getShopItem(id)
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
            addShopItemUseCase.addShopItem(item)
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
                editShopItemUseCase.editShopItem(item)
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