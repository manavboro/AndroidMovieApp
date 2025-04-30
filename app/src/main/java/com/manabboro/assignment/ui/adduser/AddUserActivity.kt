package com.manabboro.assignment.ui.adduser

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.manabboro.assignment.R

class AddUserActivity : AppCompatActivity() {

    private val viewModel: AddUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nameEditText = findViewById<EditText>(R.id.editTextName)
        val jobEditText = findViewById<EditText>(R.id.editTextJob)
        val submitButton = findViewById<Button>(R.id.buttonSubmit)

        submitButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val job = jobEditText.text.toString()
            viewModel.addUser(name, job)
            finish()
        }
    }
}