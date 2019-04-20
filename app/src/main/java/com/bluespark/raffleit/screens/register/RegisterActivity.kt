package com.bluespark.raffleit.screens.register

import android.os.Bundle
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.mvp.BaseActivityImpl

class RegisterActivity : BaseActivityImpl() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_register)
	}
}
