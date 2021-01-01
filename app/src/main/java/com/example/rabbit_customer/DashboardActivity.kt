package com.example.rabbit_customer

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_dashboard)

		val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
		val navController = findNavController(R.id.navCtrlFragment)


		bottomNavigationView.setupWithNavController(navController)
	}
}