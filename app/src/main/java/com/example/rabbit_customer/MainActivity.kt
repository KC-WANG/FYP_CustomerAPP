package com.example.rabbit_customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var tv_register: TextView
    private lateinit var etUserID: EditText
    private lateinit var etPwd: EditText
    private lateinit var btnSign: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_register = findViewById<Button>(R.id.tv_register)

        tv_register.setOnClickListener { register() }
    }

    fun login(view: View) {
        val id = findViewById<EditText>(R.id.et_login_email).text.toString()
        val password = findViewById<EditText>(R.id.et_login_password).text.toString()


        if(id == "test" && password == "abc123***") {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Invalid login ID or Password", Toast.LENGTH_SHORT).show()
        }
    }

    fun register() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}