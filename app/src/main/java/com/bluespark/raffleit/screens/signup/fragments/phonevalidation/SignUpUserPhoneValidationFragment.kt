package com.bluespark.raffleit.screens.signup.fragments.phonevalidation

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.mvp.BaseFragmentImpl
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SignUpUserPhoneValidationFragment.Listener] interface
 * to handle interaction events.
 * Use the [SignUpUserPhoneValidationFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SignUpUserPhoneValidationFragment : BaseFragmentImpl(),
	SignUpUserPhoneValidationContract.View {

	@Inject
	lateinit var presenter: SignUpUserPhoneValidationPresenterImpl

	private var listener: Listener? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		getPresentationComponent().inject(this)

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
		return inflater.inflate(R.layout.fragment_sign_up_user_phone_validation, container, false)
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

	}

	companion object {
		val TAG: String = SignUpUserPhoneValidationFragment::class.java.simpleName

		/**
		 * Factory method to create a new instance of [SignUpUserPhoneValidationFragment].
		 */
		@JvmStatic
		fun newInstance() =
			SignUpUserPhoneValidationFragment().apply {
				arguments = Bundle().apply {
				}
			}
	}

	/**
	 * [SignUpUserPhoneValidationContract.View] implementation.
	 */
	// TODO

}
