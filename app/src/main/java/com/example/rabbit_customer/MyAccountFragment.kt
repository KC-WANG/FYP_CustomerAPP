package com.example.rabbit_customer

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.rabbit_customer.models.User
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

/**
 * A simple [Fragment] subclass.
 */

class MyAccountFragment : Fragment() {
	private lateinit var btn_Logout: Button
	private lateinit var btn_Save: Button
	private lateinit var mAuth: FirebaseAuth

	private lateinit var et_profile_name: EditText
	private lateinit var et_profile_email: EditText
	private lateinit var et_profile_phone: EditText
	private lateinit var et_profile_curr_pwd: EditText
	private lateinit var et_profile_new_pwd: EditText
	private lateinit var et_profile_new_confirm_pwd: EditText

	private lateinit var rootView: View


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		rootView = inflater.inflate(R.layout.fragment_my_account, container, false)
		return rootView
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		mAuth = FirebaseAuth.getInstance()

		et_profile_name = rootView.findViewById<EditText>(R.id.et_profile_name)
		et_profile_email = rootView.findViewById<EditText>(R.id.et_profile_email)
		et_profile_phone = rootView.findViewById<EditText>(R.id.et_profile_phone)
		et_profile_curr_pwd = rootView.findViewById<EditText>(R.id.et_profile_curr_pwd)
		et_profile_new_pwd = rootView.findViewById<EditText>(R.id.et_profile_new_pwd)
		et_profile_new_confirm_pwd =
			rootView.findViewById<EditText>(R.id.et_profile_new_confirm_pwd)

		btn_Logout = rootView.findViewById<Button>(R.id.btn_Logout)
		btn_Logout.setOnClickListener { logout() }

		btn_Save = rootView.findViewById<Button>(R.id.btn_Save)
		btn_Save.setOnClickListener { save() }
	}

	override fun onResume() {
		super.onResume()
		initData()
	}

	private fun initData() {
		val user = mAuth.currentUser

		val myDf = FirebaseDatabase.getInstance().reference.child("User").child(user!!.uid)
		myDf.addValueEventListener(object : ValueEventListener {
			override fun onDataChange(dataSnapshot: DataSnapshot) {
				var userInfo = dataSnapshot.getValue(User::class.java)!!
				et_profile_name.setText(userInfo.name.toString())
				et_profile_email.setText(userInfo.email.toString())
				et_profile_phone.setText(userInfo.phone.toString())
			}

			override fun onCancelled(databaseError: DatabaseError) {}
		})

	}

	private fun save() {
		when {
			TextUtils.isEmpty(et_profile_name.text.toString().trim { it <= ' ' }) -> {
				Toast.makeText(
					activity,
					"Please Enter Name.",
					Toast.LENGTH_SHORT
				).show()
			}

			TextUtils.isEmpty(et_profile_curr_pwd.text.toString().trim { it <= ' ' }) -> {
				Toast.makeText(
					activity,
					"Please Enter Password.",
					Toast.LENGTH_SHORT
				).show()
			}

			else -> {
				val name: String = et_profile_name.text.toString().trim { it <= ' ' }
				val email: String = et_profile_email.text.toString().trim { it <= ' ' }
				val curr_pwd: String = et_profile_curr_pwd.text.toString().trim { it <= ' ' }
				val new_pwd: String = et_profile_new_pwd.text.toString().trim { it <= ' ' }
				val new_confirm_pwd: String = et_profile_new_confirm_pwd.text.toString().trim { it <= ' ' }

				val user = mAuth.currentUser!!

				val credential = EmailAuthProvider
					.getCredential(email, curr_pwd)

				user.reauthenticate(credential)
					.addOnCompleteListener {
						if(!it.isSuccessful)
					}

			}

		}
	}


	private fun logout() {
		activity?.let {
			AlertDialog.Builder(it)
				.setTitle("You are going to quit")
				.setMessage("Are you sure to Logout?")
				.setPositiveButton("Confirm",
					DialogInterface.OnClickListener { dialogInterface, _ ->
						dialogInterface.dismiss()
						mAuth.signOut()
						val intent = Intent(it, LoginActivity::class.java)
						startActivity(intent)
						it.finish()
					}).setNegativeButton("Cancel",
					DialogInterface.OnClickListener { dialogInterface, _ -> dialogInterface.dismiss() })
				.create().show()
		}
	}


}