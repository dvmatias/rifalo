package com.bluespark.raffleit.screens.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.bluespark.raffleit.screens.signup.fragments.userinfo.SignUpUserInfoFragment
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivityImpl(), SignUpContract.View, View.OnClickListener,
	SignUpUserInfoFragment.Listener {

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

	override fun setFlowButtonLabel(label: String) {
		Toast.makeText(applicationContext, "Set button label: $label", Toast.LENGTH_SHORT).show()
	}

	override fun onBackButtonClicked() {
		Toast.makeText(applicationContext, "Back clicked!", Toast.LENGTH_SHORT).show()
	}

	override fun onFlowButtonClicked() {
		Toast.makeText(applicationContext, "Flow button clicked!", Toast.LENGTH_SHORT).show()
	}

	override fun goToValidatePhoneFragment() {
		Toast.makeText(applicationContext, "Go to ValidatePhoneFragment", Toast.LENGTH_SHORT).show()
	}

	override fun goToSignUpUserInfoFragment() {
		Toast.makeText(applicationContext, "Go to SignUpUserInfoFragment", Toast.LENGTH_SHORT)
			.show()
	}

	/**
	 *
	 */

	override fun onClick(v: View?) {
		when (v?.id) {
			R.id.back_btn -> Toast.makeText(
				this,
				"Back button clicked!",
				Toast.LENGTH_SHORT
			).show()// TODO onBackButtonClicked()
			R.id.flow_btn -> Toast.makeText(
				this,
				"Flow button clicked!",
				Toast.LENGTH_SHORT
			).show()// TODO onFlowButtonClicked()
		}
	}
}
