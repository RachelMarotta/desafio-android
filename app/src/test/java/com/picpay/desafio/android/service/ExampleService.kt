package com.picpay.desafio.android.service

import com.picpay.desafio.android.test

class ExampleService(
    private val service: PicPayService
) {

    fun example(): List<User> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}