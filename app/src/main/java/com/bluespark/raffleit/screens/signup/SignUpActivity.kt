package com.bluespark.raffleit.screens.signup

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.Toast
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.bluespark.raffleit.screens.signup.fragments.phonevalidation.UserPhoneValidationFragment
import com.bluespark.raffleit.screens.signup.fragments.userinfo.UserInfoFragment
import kotlinx.android.synthetic.main.activity_sign_up.*
import javax.inject.Inject

class SignUpActivity : BaseActivityImpl(), SignUpContract.View, View.OnClickListener,
	UserInfoFragment.Listener, UserPhoneValidationFragment.Listener {

	@Inject
	lateinit var fragmentManager: FragmentManager

	@Inject
	lateinit var adapter: SignUpFragmentAdapter

	companion object {
		@Suppress("unused")
		private val TAG = SignUpActivity::class.java.simpleName
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		super.applyImmersiveFullScreen()
		setContentView(R.layout.activity_sign_up)
		getPresentationComponent().inject(this)

		setListeners()
		setupPager()
	}

	private fun setListeners() {
		back_btn.setOnClickListener(this)
		flow_btn.setOnClickListener(this)
	}

	private fun setupPager() {
		adapter.addFragment(UserInfoFragment.newInstance(), UserInfoFragment.TAG)
		adapter.addFragment(
			UserPhoneValidationFragment.newInstance(),
			UserPhoneValidationFragment.TAG
		)
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
		val currentFragment = adapter.getItem(pager.currentItem)
		if (currentFragment is UserInfoFragment)
			currentFragment.validateUser()
	}

	override fun goToValidatePhoneFragment() {
		pager.setCurrentItem(1, true)
	}

	override fun goToSignUpUserInfoFragment() {
		Toast.makeText(applicationContext, "Go to UserInfoFragment", Toast.LENGTH_SHORT)
			.show()
	}

	/**
	 * [UserInfoFragment.Listener] implementation.
	 */

	override fun onValidUser() {
		goToValidatePhoneFragment()
	}

	/**
	 *
	 */

	override fun onClick(v: View?) {
		when (v?.id) {
			R.id.back_btn -> onBackButtonClicked()
			R.id.flow_btn -> onFlowButtonClicked()
		}
	}
}
