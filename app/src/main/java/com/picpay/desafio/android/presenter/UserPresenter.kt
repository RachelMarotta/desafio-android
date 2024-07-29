package com.picpay.desafio.android.presenter

import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.model.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserPresenter(private val view: View, private val repository: UserRepository) {

    fun loadUsers() {
        view.showLoading()
        repository.getUsers().enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                view.showError()
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                view.hideLoading()
                if (response.isSuccessful) {
                    response.body()?.let {
                        view.showUsers(it)
                    } ?: view.showError()
                } else {
                    view.showError()
                }
            }
        })
    }

    interface View {
        fun showUsers(users: List<User>)
        fun showError()
        fun showLoading()
        fun hideLoading()
    }
}
