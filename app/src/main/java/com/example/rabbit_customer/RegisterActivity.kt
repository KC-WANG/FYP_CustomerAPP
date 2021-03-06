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
import com.example.rabbit_customer.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity : AppCompatActivity() {
	lateinit var tv_login: TextView
	lateinit var btn_Register: Button
	lateinit var et_register_name: EditText
	lateinit var et_register_email: EditText
	lateinit var et_register_password: EditText
	lateinit var et_register_phone: EditText


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_register)

		tv_login = findViewById<TextView>(R.id.tv_login)
		btn_Register = findViewById<Button>(R.id.btn_Register)

		et_register_name = findViewById<EditText>(R.id.et_register_name)
		et_register_email = findViewById<EditText>(R.id.et_register_email)
		et_register_password = findViewById<EditText>(R.id.et_register_password)
		et_register_phone = findViewById<EditText>(R.id.et_register_phone)

		tv_login.setOnClickListener {
			startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
		}
		btn_Register.setOnClickListener { register() }
	}

	private fun register() {
		when {
			TextUtils.isEmpty(et_register_name.text.toString().trim { it <= ' ' }) -> {
				Toast.makeText(
					this@RegisterActivity,
					"Please Enter Name.",
					Toast.LENGTH_SHORT
				).show()
			}

			TextUtils.isEmpty(et_register_email.text.toString().trim { it <= ' ' }) -> {
				Toast.makeText(
					this@RegisterActivity,
					"Please Enter E-mail.",
					Toast.LENGTH_SHORT
				).show()
			}

			TextUtils.isEmpty(et_register_password.text.toString().trim { it <= ' ' }) -> {
				Toast.makeText(
					this@RegisterActivity,
					"Please Enter Password.",
					Toast.LENGTH_SHORT
				).show()
			}

			TextUtils.isEmpty(et_register_phone.text.toString().trim { it <= ' ' }) -> {
				Toast.makeText(
					this@RegisterActivity,
					"Please Enter Phone Number.",
					Toast.LENGTH_SHORT
				).show()
			}
			else -> {

				val name: String = et_register_name.text.toString().trim { it <= ' ' }
				val email: String = et_register_email.text.toString().trim { it <= ' ' }
				val password: String = et_register_password.text.toString().trim { it <= ' ' }
				val phone: String = et_register_phone.text.toString().trim { it <= ' ' }

				// Create an instance to register
				FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
					.addOnCompleteListener { task ->

						if (task.isSuccessful) {
							val firebaseUser: FirebaseUser = task.result!!.user!!
							val uid = firebaseUser.uid

							Toast.makeText(
								this@RegisterActivity,
								"Registered Successfully.",
								Toast.LENGTH_SHORT
							).show()

							val user1 = User(uid, name, email, password, phone, "customer")
							FirebaseDatabase.getInstance().reference.child("User")
								.child(firebaseUser.uid)
								.setValue(user1)

							val intent =
								Intent(this@RegisterActivity, DashboardActivity::class.java)
							intent.flags =
								Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
							intent.putExtra("user_id", firebaseUser.uid)
							intent.putExtra("email_id", email)
							startActivity(intent)
							finish()
						} else {
							Toast.makeText(
								this@RegisterActivity,
								task.exception!!.message.toString(),
								Toast.LENGTH_SHORT
							).show()
						}
					}
			}
		}
	}
}