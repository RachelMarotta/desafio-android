package com.picpay.desafio.android.model.repository

import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.service.PicPayService
import retrofit2.Call

class UserRepository(private val service: PicPayService) {
    fun getUsers(): Call<List<User>> = service.getUsers()
}
