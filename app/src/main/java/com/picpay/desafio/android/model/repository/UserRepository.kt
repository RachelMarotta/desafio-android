package com.picpay.desafio.android.model.repository

import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.model.dao.UserDao
import com.picpay.desafio.android.service.PicPayService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

class UserRepository(private val service: PicPayService, private val userDao: UserDao) {
    fun getUsers(): Call<List<User>> = service.getUsers()

    suspend fun getUsersFromApi(): List<User>? = withContext(Dispatchers.IO) {
        val response = service.getUsers().execute()
        if (response.isSuccessful) {
            response.body()?.also { users ->
                userDao.insertAllUsers(users)
            }
        } else {
            emptyList()
        }
    }

    suspend fun getUsersFromDb(): List<User> = withContext(Dispatchers.IO) {
        userDao.getAllUsers()
    }
}
