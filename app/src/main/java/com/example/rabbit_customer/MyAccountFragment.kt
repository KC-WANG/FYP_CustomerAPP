package com.example.rabbit_customer

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

/**
 * A simple [Fragment] subclass.
 */

class MyAccountFragment : Fragment() {
	private lateinit var btn_Logout: Button
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
		btn_Logout = rootView.findViewById(R.id.btn_Logout)

		btn_Logout.setOnClickListener { logout() }
	}

	override fun onResume() {
		super.onResume()
		initData()
	}

	private fun initData() {
		mAuth = FirebaseAuth.getInstance()
		val user = mAuth.currentUser

		et_profile_name = rootView.findViewById(R.id.et_profile_name)
		et_profile_email = rootView.findViewById(R.id.et_profile_email)
		et_profile_phone = rootView.findViewById(R.id.et_profile_phone)
		et_profile_curr_pwd = rootView.findViewById(R.id.et_profile_curr_pwd)
		et_profile_new_pwd = rootView.findViewById(R.id.et_profile_new_pwd)
		et_profile_new_confirm_pwd = rootView.findViewById(R.id.et_profile_new_confirm_pwd)


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