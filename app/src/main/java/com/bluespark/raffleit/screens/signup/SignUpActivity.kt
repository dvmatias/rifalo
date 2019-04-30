package com.bluespark.raffleit.screens.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.Constants.Companion.EXTRA_KEY_COUNTRY_LIST
import com.bluespark.raffleit.common.Constants.Companion.EXTRA_KEY_SELECTED_COUNTRY
import com.bluespark.raffleit.common.Constants.Companion.REQUEST_CODE_CHOOSE_COUNTRY_ACTIVITY
import com.bluespark.raffleit.common.model.objects.Country
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.bluespark.raffleit.common.utils.managers.DialogsManager
import com.bluespark.raffleit.common.views.CountryCodeSelector
import com.bluespark.raffleit.common.views.LoadingDialogFragment
import com.bluespark.raffleit.screens.choosecountry.ChooseCountryActivity
import com.bluespark.raffleit.screens.signup.fragments.phonevalidation.UserPhoneValidationFragment
import com.bluespark.raffleit.screens.signup.fragments.userinfo.UserInfoFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sign_up.*
import javax.inject.Inject


class SignUpActivity : BaseActivityImpl(), SignUpContract.View, View.OnClickListener,
	UserInfoFragment.Listener, UserPhoneValidationFragment.Listener,
	CountryCodeSelector.Listener {

	@Inject
	lateinit var presenter: SignUpPresenterImpl
	@Inject
	lateinit var adapter: SignUpFragmentAdapter
	@Inject
	lateinit var dialogsManager: DialogsManager
	@Inject
	lateinit var loadingDialogFragment: LoadingDialogFragment
	@Inject
	lateinit var gson: Gson

	private lateinit var selectedCountry: Country

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
		setSelectedcountry(
			Country(
				"XX",
				"+0",
				"Default",
				"https://firebasestorage.googleapis.com/v0/b/rifalo-805c2.appspot.com/o/images_country_codes%2Fcountry_code_default.png?alt=media&token=f3e29d6e-3aa3-4901-9d0e-7f31b26b21ce"
			)
		)
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

	private fun getCurrentFragment(): Fragment = adapter.getItem(pager.currentItem)

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

	override fun showSelectedCountry() {
		val currentFragment = getCurrentFragment()
		if (currentFragment is UserPhoneValidationFragment)
			currentFragment.showSelectedCountry(this.selectedCountry)
	}

	override fun onBackButtonClicked() {
		Toast.makeText(applicationContext, "Back clicked!", Toast.LENGTH_SHORT).show()
	}

	override fun onFlowButtonClicked() {
		val currentFragment = getCurrentFragment()
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

	override fun goToChooseCountryScreen() {
		val intent = Intent(this, ChooseCountryActivity::class.java)
		intent.putExtra(
			EXTRA_KEY_SELECTED_COUNTRY,
			gson.toJson(selectedCountry)
		)
		intent.putExtra(
			EXTRA_KEY_COUNTRY_LIST,
			gson.toJson(presenter.countryList)
		)
		startActivityForResult(intent, REQUEST_CODE_CHOOSE_COUNTRY_ACTIVITY)
	}

	override fun setSelectedcountry(country: Country) {
		this.selectedCountry = country
		showSelectedCountry()
	}

	/**
	 * [UserInfoFragment.Listener] implementation.
	 */

	override fun onValidUser() {
		goToValidatePhoneFragment()
	}

	/**
	 * [CountryCodeSelector.Listener] implementation.
	 */
	// TODO
	override fun onCountryClick() {
		goToChooseCountryScreen()
	}

	override fun onPhoneEmpty() {
		// Disable terms and conditions view.
		val currentFragment = getCurrentFragment()
		if (currentFragment is UserPhoneValidationFragment)
			currentFragment.enableTermsAndConditions(false)
	}

	override fun onPhoneNotEmpty() {
		// Enable terms and conditions view.
		val currentFragment = getCurrentFragment()
		if (currentFragment is UserPhoneValidationFragment)
			currentFragment.enableTermsAndConditions(false)
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

	/**
	 * On Activity Result.
	 */

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == REQUEST_CODE_CHOOSE_COUNTRY_ACTIVITY) {
			val selectedCountry = when (resultCode) {
				Activity.RESULT_OK -> {
					gson.fromJson(
						data?.getStringExtra(EXTRA_KEY_SELECTED_COUNTRY),
						Country::class.java
					)
				}
				Activity.RESULT_CANCELED -> this.selectedCountry
				else -> Country(
					"XX",
					"+0",
					"Default",
					"https://firebasestorage.googleapis.com/v0/b/rifalo-805c2.appspot.com/o/images_country_codes%2Fcountry_code_default.png?alt=media&token=f3e29d6e-3aa3-4901-9d0e-7f31b26b21ce"
				)
			}
			setSelectedcountry(selectedCountry)
		}
	}
}
