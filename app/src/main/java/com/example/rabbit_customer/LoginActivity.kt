package com.example.rabbit_customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
	private lateinit var tv_register: TextView
	private lateinit var et_login_email: EditText
	private lateinit var et_login_password: EditText
	private lateinit var btn_Login: Button

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)

		et_login_email = findViewById<EditText>(R.id.et_login_email)
		et_login_password = findViewById<EditText>(R.id.et_login_password)

		tv_register = findViewById<Button>(R.id.tv_register)
		tv_register.setOnClickListener {
			startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
		}

		btn_Login = findViewById<Button>(R.id.btn_Login)
		btn_Login.setOnClickListener { login() }
	}

	private fun login() {
		when {
			TextUtils.isEmpty(et_login_email.text.toString().trim { it <= ' ' }) -> {
				Toast.makeText(
					this@LoginActivity,
					"Please Enter E-mail.",
					Toast.LENGTH_SHORT
				).show()
			}

			TextUtils.isEmpty(et_login_password.text.toString().trim { it <= ' ' }) -> {
				Toast.makeText(
					this@LoginActivity,
					"Please Enter Password.",
					Toast.LENGTH_SHORT
				).show()
			}

			else -> {

				val email: String = et_login_email.text.toString().trim { it <= ' ' }
				val password: String = et_login_password.text.toString().trim { it <= ' ' }

				// Create an instance to register
				FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
					.addOnCompleteListener { task ->

						if (task.isSuccessful) {
							Toast.makeText(
								this@LoginActivity,
								"Login Successfully.",
								Toast.LENGTH_SHORT
							).show()

							val intent =
								Intent(this@LoginActivity, DashboardActivity::class.java)
							intent.flags =
								Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
							startActivity(intent)
							finish()
						} else {
							Toast.makeText(
								this@LoginActivity,
								task.exception!!.message.toString(),
								Toast.LENGTH_SHORT
							).show()
						}
					}
			}
		}
	}

}