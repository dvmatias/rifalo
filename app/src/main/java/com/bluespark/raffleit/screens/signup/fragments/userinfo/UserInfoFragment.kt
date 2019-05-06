package com.bluespark.raffleit.screens.signup.fragments.userinfo

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.mvp.BaseFragmentImpl
import kotlinx.android.synthetic.main.fragment_user_info.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UserInfoFragment.Listener] interface
 * to handle interaction events.
 * Use the [UserInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class UserInfoFragment : BaseFragmentImpl(), UserInfoContract.View {

	@Inject
	lateinit var presenter: UserInfoPresenterImpl

	private var listener: Listener? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		getPresentationComponent().inject(this)

		arguments?.let {
		}

	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// TODO delete this only used to switch fragments quickly.
		etcv_user_email.setText("masacresita@gmail.com")
		etcv_user_password.setText("mas@1234")
		etcv_user_password_confirmation.setText("mas@1234")
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_user_info, container, false)
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
		fun onValidEmailAndPassword(email: String, password: String)
	}

	companion object {
		val TAG: String = UserInfoFragment::class.java.simpleName

		/**
		 * Factory method to create a new instance of [UserInfoFragment].
		 */
		@JvmStatic
		fun newInstance() =
			UserInfoFragment().apply {
				arguments = Bundle().apply {
				}
			}
	}

	/**
	 * [UserInfoContract.View] implementation.
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

	override fun hideErrors() {
		etcv_user_email.setStatusNormal()
		etcv_user_password.setStatusNormal()
		etcv_user_password_confirmation.setStatusNormal()
	}

	override fun onValidEmailAndPassword(email: String, password: String) {
		listener?.onValidEmailAndPassword(email, password)
	}

	fun validateEmailAndPassword() {
		presenter.validateEmailAndPassword(
			etcv_user_email.getText(),
			etcv_user_password.getText(),
			etcv_user_password_confirmation.getText()
		)
	}

}
