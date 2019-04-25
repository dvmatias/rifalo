package com.bluespark.raffleit.screens.signup

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.bluespark.raffleit.screens.signup.fragments.SignUpUserInfoFragment
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivityImpl(), SignUpContract.View, View.OnClickListener, SignUpUserInfoFragment.Listener {

	private var adapter: SignUpFragmentAdapter = SignUpFragmentAdapter(supportFragmentManager)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		super.applyImmersiveFullScreen()
		setContentView(R.layout.activity_sign_up)

		setListeners()
		setupPager()
	}

	private fun setListeners() {
		back_btn.setOnClickListener(this)
		flow_btn.setOnClickListener(this)
	}

	private fun setupPager() {
		adapter.addFragment(SignUpUserInfoFragment.newInstance(), SignUpUserInfoFragment.TAG)
		adapter.addFragment(SignUpUserInfoFragment.newInstance(), "TITLE_B")
		pager.adapter = adapter
	}

	/**
	 * [SignUpContract.View] implementation.
	 */

	override fun onBackButtonClicked() {
		Toast.makeText(applicationContext, "Back clicked!", Toast.LENGTH_SHORT).show()
	}

	override fun onFlowButtonClicked() {
		Toast.makeText(applicationContext, "Flow button clicked!", Toast.LENGTH_SHORT).show()
	}

	override fun setFlowButtonLabel(label: String) {
		Toast.makeText(applicationContext, "Set flow button label: $label", Toast.LENGTH_SHORT)
			.show()
	}

	override fun setEmailError(errorMsg: String) {
		Toast.makeText(applicationContext, "Set email error: $errorMsg", Toast.LENGTH_SHORT)
			.show()
	}

	override fun setPasswordError(errorMsg: String) {
		Toast.makeText(applicationContext, "Set password error: $errorMsg", Toast.LENGTH_SHORT)
			.show()
	}

	override fun setPasswordConfirmationError(errorMsg: String) {
		Toast.makeText(applicationContext, "Set password confirmation error: $errorMsg", Toast.LENGTH_SHORT)
			.show()
	}

	override fun goToValidatePhoneFragment() {
		Toast.makeText(applicationContext, "Go to validate phone fragment", Toast.LENGTH_SHORT)
			.show()
	}

	override fun goToSignUpUserInfoFragment() {
		Toast.makeText(applicationContext, "Go to sign up user info fragment", Toast.LENGTH_SHORT)
			.show()
	}

	/**
	 *
	 */

	override fun onFragmentInteraction(uri: Uri) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}


	/**
	 *
	 */

	override fun onClick(v: View?) {
		when(v?.id) {
			R.id.back_btn -> onBackButtonClicked()
			R.id.flow_btn -> onFlowButtonClicked()
		}
	}
}
