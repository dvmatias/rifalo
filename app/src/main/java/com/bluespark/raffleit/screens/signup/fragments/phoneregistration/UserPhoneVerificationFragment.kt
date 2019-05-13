package com.bluespark.raffleit.screens.signup.fragments.phoneregistration

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.mvp.BaseFragmentImpl
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.android.synthetic.main.fragment_user_phone_verification.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UserPhoneVerificationFragment.Listener] interface
 * to handle interaction events.
 * Use the [UserPhoneVerificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 * @author matias.delv.dom@gmail.com
 */
class UserPhoneVerificationFragment : BaseFragmentImpl(), UserPhoneVerificationContract.View {

	@Inject
	lateinit var presenter: UserPhoneVerificationPresenterImpl

	private var listener: Listener? = null
	private var otpCode: String = ""

	companion object {
		val TAG: String = UserPhoneVerificationFragment::class.java.simpleName

		/**
		 * Factory method to create a new instance of [UserPhoneVerificationFragment].
		 */
		@JvmStatic
		fun newInstance() =
			UserPhoneVerificationFragment().apply {
				arguments = Bundle().apply {
				}
			}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		getPresentationComponent().inject(this)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		etcv_otp.setTextChangedListener(object : TextWatcher {
			override fun afterTextChanged(s: Editable?) {}

			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
				etcv_otp.setStatusNormal()
				otpCode = s.toString()
			}
		})
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_user_phone_verification, container, false)
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		if (context is Listener) {
			listener = context
		} else {
			throw RuntimeException("Calling Activity must implement UserPhoneVerificationFragment.Listener")
		}
	}

	override fun onDetach() {
		super.onDetach()
		listener = null
	}

	/**
	 * [UserPhoneVerificationContract.View] implementation.
	 */

	override fun showLoadingDialog(show: Boolean) {
		listener?.onLoading(show)
	}

	override fun onVerifiedPhone(phoneNumber: String) {
		listener?.onVerifiedPhone(phoneNumber)
	}

	override fun writeAutoOtpCode(otpCode: String) {
		etcv_otp.setText(otpCode)
	}

	override fun showOtpInlineError(errorMsg: String) {
		etcv_otp.setStatusError(errorMsg)
	}

	override fun onPhoneAuthCredentialCreated(phoneAuthCredential: PhoneAuthCredential) {
		listener?.onPhoneAuthCredentialCreated(phoneAuthCredential)
	}

	/**
	 * Interface to be implemented by calling Activity. This interface is the bridge to communicate
	 * this fragment with his parent Activity.
	 */
	interface Listener {
		fun onLoading(show: Boolean)
		fun onVerifiedPhone(phoneNumber: String)
		fun onPhoneAuthCredentialCreated(phoneAuthCredential: PhoneAuthCredential)
	}

	fun sendOtpCode(phoneNumber: String) {
		Handler().postDelayed({
			presenter.sendOtpCode(phoneNumber)
		}, 400)
	}

	fun verifyOtp() {
		presenter.verifyOtpCode(otpCode)
	}

}