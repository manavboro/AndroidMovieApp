package com.manabboro.assignment.ui.userlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.manabboro.assignment.R
import com.manabboro.assignment.ui.adduser.AddUserActivity
import com.manabboro.assignment.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class UserListActivity : AppCompatActivity() {
    private val viewModel: UserViewModel by viewModels()
    private var userAdapter: UserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userAdapter = UserAdapter { user ->
            {

            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewUsers)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = userAdapter?.withLoadStateFooter(
            footer = UserLoadStateAdapter { userAdapter?.retry() }
        )


        lifecycleScope.launchWhenStarted {
            viewModel.users.collectLatest { pagingData ->
                userAdapter?.submitData(pagingData)
            }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fabAddUser)
        fab.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)

        }
    }
}