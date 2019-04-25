package com.bluespark.raffleit.screens.signup.fragments.userinfo

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.mvp.BaseFragmentImpl
import kotlinx.android.synthetic.main.fragment_sign_up_user_info.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SignUpUserInfoFragment.Listener] interface
 * to handle interaction events.
 * Use the [SignUpUserInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SignUpUserInfoFragment : BaseFragmentImpl(), SignUpUserInfoContract.View {
	private var listener: Listener? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
		}

	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_sign_up_user_info, container, false)
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
	 * TODO desc
	 */
	interface Listener {
		// TODO
	}

	companion object {
		val TAG: String = SignUpUserInfoFragment::class.java.simpleName

		/**
		 * Factory method to create a new instance of [SignUpUserInfoFragment].
		 */
		@JvmStatic
		fun newInstance() =
			SignUpUserInfoFragment().apply {
				arguments = Bundle().apply {
				}
			}
	}

	/**
	 * [SignUpUserInfoContract.View] implementation.
	 */

	override fun setEmailError(errorMsg: String) {
		etcv_user_email.setStatusError(errorMsg)
	}

	override fun setPasswordError(errorMsg: String) {
		etcv_user_password.setStatusError(errorMsg)
	}

	override fun setPasswordConfirmationError(errorMsg: String) {
		etcv_user_password_confirmation.setStatusError(errorMsg)
	}

}
