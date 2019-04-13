package com.bluespark.raffleit.screens.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bluespark.raffleit.R

class MainActivity : AppCompatActivity() {

	companion object {
		@Suppress("unused")
		private val TAG = MainActivity::class.java.simpleName
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
	}
}