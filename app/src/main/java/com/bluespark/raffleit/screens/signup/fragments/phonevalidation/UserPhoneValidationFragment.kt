package com.bluespark.raffleit.screens.signup.fragments.phonevalidation

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.model.objects.Country
import com.bluespark.raffleit.common.mvp.BaseFragmentImpl
import com.bluespark.raffleit.common.utils.ImageLoader
import com.bluespark.raffleit.common.utils.managers.PhoneManager
import com.bluespark.raffleit.screens.signup.SignUpActivity
import kotlinx.android.synthetic.main.fragment_user_phone_validation.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UserPhoneValidationFragment.Listener] interface
 * to handle interaction events.
 * Use the [UserPhoneValidationFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class UserPhoneValidationFragment : BaseFragmentImpl(),
	UserPhoneValidationContract.View {

	@Inject
	lateinit var presenter: UserPhoneValidationPresenterImpl
	@Inject
	lateinit var imageLoader: ImageLoader
	@Inject
	lateinit var phoneManager: PhoneManager

	private var listener: Listener? = null

	companion object {
		val TAG: String = UserPhoneValidationFragment::class.java.simpleName

		/**
		 * Factory method to create a new instance of [UserPhoneValidationFragment].
		 */
		@JvmStatic
		fun newInstance() =
			UserPhoneValidationFragment().apply {
				arguments = Bundle().apply {}
			}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		getPresentationComponent().inject(this)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		// By default, terms and conditions is disables until valid phone has been written.
		enableTermsAndConditions(false)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_user_phone_validation, container, false)
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		if (context is Listener) {
			listener = context
		} else {
			throw RuntimeException("$context must implement Listener")
		}
	}

	override fun onDetach() {
		super.onDetach()
		listener = null
	}

	/**
	 * [UserPhoneValidationContract.View] implementation.
	 */

	override fun showSelectedCountry(country: Country) {
		v_country_code_selector.showCountryInfo(country, imageLoader, phoneManager)
	}

	override fun showLoading(show: Boolean) {
		listener?.showLoadingDialog(show)
	}

	override fun showInlinePhoneError(validCountry: Boolean, validNumber: Boolean) {
		var errorMsg = ""
		if (!validCountry && !validNumber) {
			errorMsg += activity?.getString(R.string.msg_error_country_and_number)
		} else {
			if (!validCountry)
				errorMsg += activity?.getString(R.string.msg_error_country)
			if (!validNumber)
				errorMsg += activity?.getString(R.string.msg_error_number)
		}
		v_country_code_selector.showError(errorMsg)
	}

	override fun hideInlinePhoneError() {
		v_country_code_selector.hideError()
	}

	override fun onValidPhone(phoneNumber: String) {
		listener?.goToRegisterPhoneFragment(phoneNumber)
	}

	override fun enableTermsAndConditions(isEnabled: Boolean) {
		v_agreement._isEnabled = isEnabled
	}

	/**
	 * Interface to be implemented by calling Activity. This interface is the bridge to communicate
	 * this fragment with his parent Activity.
	 */
	interface Listener {
		fun goToRegisterPhoneFragment(phoneNumber: String)
		fun showLoadingDialog(show: Boolean)
	}

	/**
	 * Triggered with [SignUpActivity.onFlowButtonClicked].
	 *
	 * This method start the country code and phone number validation flow. If all the info is
	 * valid, the firebase phone number verification should start.
	 */
	fun validatePhone() {
		presenter.validatePhoneNumber(
			v_country_code_selector.country.code,
			v_country_code_selector.phoneNumber,
			v_country_code_selector.country.dial_code
		)
	}

}