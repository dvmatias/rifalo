package com.bluespark.raffleit.screens.register

import android.net.Uri
import android.os.Bundle
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.bluespark.raffleit.screens.register.fragments.RegisterUserInfoFragment
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivityImpl(), RegisterUserInfoFragment.Listener {

	private var adapter: RegisterFragmentAdapter = RegisterFragmentAdapter(supportFragmentManager)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		super.applyImmersiveFullScreen()
		setContentView(R.layout.activity_register)

		adapter.addFragment(RegisterUserInfoFragment.newInstance(), "TITLE_A")
		adapter.addFragment(RegisterUserInfoFragment.newInstance(), "TITLE_B")
		pager.adapter = adapter
	}

	/**
	 *
	 */
	override fun onFragmentInteraction(uri: Uri) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}
