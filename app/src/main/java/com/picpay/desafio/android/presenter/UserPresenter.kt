package com.picpay.desafio.android.presenter

import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.model.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserPresenter(private val view: View, private val repository: UserRepository) {

    fun loadUsers() {
        view.showLoading()
        CoroutineScope(Dispatchers.Main).launch {
            val usersFromDb = repository.getUsersFromDb()
            if (usersFromDb.isNotEmpty()) {
                view.showUsers(usersFromDb)
                view.hideLoading()
            } else {
                val usersFromApi = repository.getUsersFromApi()
                if (usersFromApi != null) {
                    view.showUsers(usersFromApi)
                } else {
                    view.showError()
                }
                view.hideLoading()
            }
        }
    }

    interface View {
        fun showUsers(users: List<User>)
        fun showError()
        fun showLoading()
        fun hideLoading()
    }
}
