package com.picpay.desafio.android.view

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.model.repository.UserRepository
import com.picpay.desafio.android.presenter.UserPresenter
import com.picpay.desafio.android.service.ApiClient.retrofit
import com.picpay.desafio.android.service.PicPayService
import com.picpay.desafio.android.view.adapter.UserListAdapter

class MainActivity : AppCompatActivity(R.layout.activity_main), UserPresenter.View {
    private lateinit var presenter: UserPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private var recyclerViewState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = UserListAdapter()

        val service = retrofit.create(PicPayService::class.java)
        val repository = UserRepository(service)

        presenter = UserPresenter(this, repository)
        presenter.loadUsers()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        recyclerViewState = recyclerView.layoutManager?.onSaveInstanceState()
        outState.putParcelable("RECYCLER_VIEW_STATE", recyclerViewState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        recyclerViewState = savedInstanceState.getParcelable("RECYCLER_VIEW_STATE")
    }

    override fun showUsers(users: List<User>) {
        (recyclerView.adapter as UserListAdapter).users = users

        recyclerViewState?.let {
            recyclerView.layoutManager?.onRestoreInstanceState(it)
            recyclerViewState = null
        }
    }

    override fun showError() {
        Toast.makeText(this, "Error loading users", Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }
}
