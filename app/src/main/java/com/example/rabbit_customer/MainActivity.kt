package com.example.rabbit_customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun login(view: View) {
        val id = findViewById<EditText>(R.id.loginID).text.toString()
        val password = findViewById<EditText>(R.id.loginPW).text.toString()


        if(id == "test" && password == "abc123***") {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Invalid login ID or Password", Toast.LENGTH_SHORT).show()
        }
    }
}