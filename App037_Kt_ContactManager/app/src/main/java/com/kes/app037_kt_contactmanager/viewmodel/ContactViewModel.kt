package com.kes.app037_kt_contactmanager.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kes.app037_kt_contactmanager.room.Contact
import com.kes.app037_kt_contactmanager.room.Repository
import kotlinx.coroutines.launch

class ContactViewModel (
    private val repository: Repository
) : ViewModel(), Observable {

    val contacts = repository.contacts
    private var contactToUpdateOrDelete: Contact? = null



    // Input fields
    @Bindable
    val inputName = MutableLiveData<String?>()
    @Bindable
    val inputEmail = MutableLiveData<String?>()

    // input status
    var isUpdateOrDelete = MutableLiveData(false)


    private fun update(contact: Contact) = viewModelScope.launch {
        repository.update(contact)
        reset()
    }

    private fun insert(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
        reset()
    }

    private fun delete(contact: Contact) = viewModelScope.launch {
        repository.delete(contact)
        reset()
    }

    private fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
        reset()
    }

    fun initUpdateOrDelete(contact: Contact) {
        inputName.value = contact.name
        inputEmail.value = contact.email
        contactToUpdateOrDelete = contact
        isUpdateOrDelete.value = true
    }

    private fun reset() {
        inputName.value = null
        inputEmail.value = null
        contactToUpdateOrDelete = null
        isUpdateOrDelete.value = false
    }

    fun saveOrUpdate() {
        if (isUpdateOrDelete.value == true && contactToUpdateOrDelete != null) {
            contactToUpdateOrDelete!!.name = inputName.value!!
            contactToUpdateOrDelete!!.email = inputEmail.value!!
            update(contactToUpdateOrDelete!!)
        } else {
            val name = inputName.value!!
            val email = inputEmail.value!!

            insert(Contact(0, name, email))
            reset()
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete.value == true && contactToUpdateOrDelete != null) {
            delete(contactToUpdateOrDelete!!)
            reset()
        } else {
            clearAll()
        }
    }

    private fun updateButtonsText() {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
//        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
//        TODO("Not yet implemented")
    }
}