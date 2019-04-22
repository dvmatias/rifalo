package com.bluespark.raffleit.screens.signup

import android.net.Uri
import android.os.Bundle
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.bluespark.raffleit.screens.signup.fragments.SignUpUserInfoFragment
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivityImpl(), SignUpUserInfoFragment.Listener {

	private var adapter: SignUpFragmentAdapter = SignUpFragmentAdapter(supportFragmentManager)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		super.applyImmersiveFullScreen()
		setContentView(R.layout.activity_sign_up)

		adapter.addFragment(SignUpUserInfoFragment.newInstance(), "TITLE_A")
		adapter.addFragment(SignUpUserInfoFragment.newInstance(), "TITLE_B")
		pager.adapter = adapter
	}

	/**
	 *
	 */
	override fun onFragmentInteraction(uri: Uri) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}
