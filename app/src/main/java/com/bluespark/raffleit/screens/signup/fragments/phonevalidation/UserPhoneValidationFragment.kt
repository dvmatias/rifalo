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
	lateinit var imageLoader: ImageLoader
	@Inject
	lateinit var phoneManager: PhoneManager
	@Inject
	lateinit var presenter: UserPhoneValidationPresenterImpl

	private var listener: Listener? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		getPresentationComponent().inject(this)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

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

	fun showSelectedCountry(selectedCountry: Country) {
		v_country_code_selector.showCountryInfo(selectedCountry, imageLoader, phoneManager)
	}

	/**
	 * TODO desc
	 */
	interface Listener {

	}

	companion object {
		val TAG: String = UserPhoneValidationFragment::class.java.simpleName

		/**
		 * Factory method to create a new instance of [UserPhoneValidationFragment].
		 */
		@JvmStatic
		fun newInstance() =
			UserPhoneValidationFragment().apply {
				arguments = Bundle().apply {
				}
			}
	}

	/**
	 * [UserPhoneValidationContract.View] implementation.
	 */
	// TODO

}
