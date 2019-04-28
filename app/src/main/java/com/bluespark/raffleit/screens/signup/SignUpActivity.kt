package com.bluespark.raffleit.screens.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.bluespark.raffleit.common.utils.managers.DialogsManager
import com.bluespark.raffleit.common.views.LoadingDialogFragment
import com.bluespark.raffleit.screens.signup.fragments.phonevalidation.UserPhoneValidationFragment
import com.bluespark.raffleit.screens.signup.fragments.userinfo.UserInfoFragment
import kotlinx.android.synthetic.main.activity_sign_up.*
import javax.inject.Inject


class SignUpActivity : BaseActivityImpl(), SignUpContract.View, View.OnClickListener,
	UserInfoFragment.Listener, UserPhoneValidationFragment.Listener {

	@Inject
	lateinit var presenter: SignUpPresenterImpl

	@Inject
	lateinit var adapter: SignUpFragmentAdapter

	@Inject
	lateinit var dialogsManager: DialogsManager

	@Inject
	lateinit var loadingDialogFragment: LoadingDialogFragment

	companion object {
		@Suppress("unused")
		private val TAG = SignUpActivity::class.java.simpleName
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		super.applyImmersiveFullScreen()
		setContentView(R.layout.activity_sign_up)
		getPresentationComponent().inject(this)

		setFlowButtonLabel(getString(R.string.label_btn_next))
		setListeners()
		setupPager()
	}

	override fun onResume() {
		super.onResume()
		presenter.fetchCountryCodes()
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
		flow_btn.labelText = label
	}

	override fun showLoading(show: Boolean) {
		if (show) {
			dialogsManager.showRetainedDialogWithId(
				loadingDialogFragment,
				LoadingDialogFragment.TAG
			)
		} else {
			dialogsManager.dismissCurrentlyShownDialog()
		}
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
		setFlowButtonLabel(getString(R.string.label_btn_validate_phone))
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
