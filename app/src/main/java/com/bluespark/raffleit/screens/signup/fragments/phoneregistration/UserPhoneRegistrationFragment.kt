package com.bluespark.raffleit.screens.signup.fragments.phoneregistration

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluespark.raffleit.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UserPhoneRegistrationFragment.Listener] interface
 * to handle interaction events.
 * Use the [UserPhoneRegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 * @author matias.delv.dom@gmail.com
 */
class UserPhoneRegistrationFragment : Fragment() {
	private var listener: Listener? = null

	companion object {
		val TAG: String = UserPhoneRegistrationFragment::class.java.simpleName

		/**
		 * Factory method to create a new instance of [UserPhoneRegistrationFragment].
		 */
		@JvmStatic
		fun newInstance() =
			UserPhoneRegistrationFragment().apply {
				arguments = Bundle().apply {}
			}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_user_phone_registration, container, false)
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		if (context is Listener) {
			listener = context
		} else {
			throw RuntimeException("Calling Activity must implement UserPhoneRegistrationFragment.Listener")
		}
	}

	override fun onDetach() {
		super.onDetach()
		listener = null
	}

	/**
	 * Interface to be implemented by calling Activity. This interface is the bridge to communicate
	 * this fragment with his parent Activity.
	 */
	interface Listener {
	}

}