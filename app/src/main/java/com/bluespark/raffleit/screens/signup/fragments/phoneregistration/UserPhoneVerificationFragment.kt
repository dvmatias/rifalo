package com.bluespark.raffleit.screens.signup.fragments.phoneregistration

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.mvp.BaseFragmentImpl
import com.bluespark.raffleit.common.utils.managers.FirebaseSignInPhoneManager
import com.bluespark.raffleit.screens.signup.SignUpActivity
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
	@Inject
	lateinit var firebaseSignInPhoneManager: FirebaseSignInPhoneManager

	private var listener: Listener? = null

	private lateinit var phoneNumber: String

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

	/**
	 * Interface to be implemented by calling Activity. This interface is the bridge to communicate
	 * this fragment with his parent Activity.
	 */
	interface Listener {
		fun onLoading(show: Boolean)
		fun onVerifiedPhone(phoneNumber: String)
	}

	fun sendOtpCode(phoneNumber: String) {
		Handler().postDelayed({
			presenter.sendOtpCode(phoneNumber)
		}, 750)
	}

	fun verifyOtp() {
		firebaseSignInPhoneManager.firebaseSignInWithPhone(
			activity as SignUpActivity,
			presenter.verificationId!!,
			etcv_otp.getText()
		)
	}

}