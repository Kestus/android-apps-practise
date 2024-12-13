package com.kes.app049_kt_coroutineflow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object UsersRepository {

    private val users = mutableListOf(
        "Bib",
        "Bob",
        "Blorg"
    )

    suspend fun addUser(user: String) {
        delay(10)
        users.add(user)
    }

    suspend fun loadUsers(): Flow<List<String>> = flow {
        delay(30)
        emit(users.toList())
    }

}