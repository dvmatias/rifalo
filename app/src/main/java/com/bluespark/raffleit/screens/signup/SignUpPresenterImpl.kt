package com.bluespark.raffleit.screens.signup

import android.os.Handler
import android.support.annotation.NonNull
import com.bluespark.raffleit.common.Constants
import com.bluespark.raffleit.common.model.databaseschemas.CountryCodeSchema
import com.bluespark.raffleit.common.model.objects.Country
import com.bluespark.raffleit.common.model.objects.SignUpUser
import com.bluespark.raffleit.common.mvp.BasePresenterImpl
import com.bluespark.raffleit.screens.signup.interactors.RegisterFirebaseUserInteractor
import com.bluespark.raffleit.screens.signup.interactors.SendVerificationEmailInteractor
import com.bluespark.raffleit.screens.signup.interactors.SignUpFetchCountryCodesInteractor
import com.bluespark.raffleit.screens.signup.interactors.UpdatePhoneFirebaseUserInteractor
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import java.util.*
import kotlin.collections.ArrayList

class SignUpPresenterImpl(
	view: SignUpContract.View,
	private var signUpFetchCountryCodesInteractor: SignUpFetchCountryCodesInteractor,
	private var registerFirebaseUserInteractor: RegisterFirebaseUserInteractor,
	private var updatePhoneFirebaseUserInteractor: UpdatePhoneFirebaseUserInteractor,
	private var sendVerificationEmailInteractor: SendVerificationEmailInteractor
) : BasePresenterImpl<SignUpContract.View>(),
	SignUpContract.Presenter, SignUpFetchCountryCodesInteractor.Listener {

	private var signUpUser: SignUpUser? = null
	private var phoneAuthCredential: PhoneAuthCredential? = null
	private var countryCodeScheme: CountryCodeSchema? = null
	var countryList: ArrayList<Country>? = null

	init {
		bind(view)
		countryList = ArrayList()
	}

	override fun fetchCountryCodes() {
		if (countryCodeScheme == null) {
			view?.showLoadingDialog(Constants.SHOW_LOADING)
			Handler().postDelayed({
				signUpFetchCountryCodesInteractor.execute(this)
			}, Constants.DELAY_FETCH_COUNTRY_CODES)
		}
	}

	/**
	 * Register user by email/password sign in method.
	 */
	override fun registerFirebaseUser(
		signUpUser: SignUpUser,
		phoneAuthCredential: PhoneAuthCredential
	) {
		this.signUpUser = signUpUser
		this.phoneAuthCredential = phoneAuthCredential
		view?.showLoadingDialog(Constants.SHOW_LOADING)
		registerFirebaseUserInteractor.execute(registerFirebaseUserInteractorListener, signUpUser)
	}

	private fun setCountryList() {
		if (countryCodeScheme != null) {
			for (languageList in countryCodeScheme!!.language) {
				if (Locale.getDefault().language.equals(languageList.code, true)) {
					for (countrySchemaList in languageList.countrySchemaList) {
						countrySchemaList.let {
							countryList!!.add(
								Country(
									it.code,
									it.dial_code,
									it.name,
									it.url
								)
							)
						}
					}
					break
				}
			}
		}
	}

	/**
	 * [SignUpFetchCountryCodesInteractor.Listener] implementation.
	 */

	override fun onSuccess(@NonNull countryCodeScheme: CountryCodeSchema) {
		this.countryCodeScheme = countryCodeScheme
		setCountryList()
		view?.showLoadingDialog(Constants.HIDE_LOADING)
	}

	override fun onFail() {
		view?.showLoadingDialog(Constants.HIDE_LOADING)
	}

	/**
	 * [RegisterFirebaseUserInteractor.Listener] implementation.
	 */

	private val registerFirebaseUserInteractorListener =
		object : RegisterFirebaseUserInteractor.Listener {
			override fun onSuccess(firebaseUser: FirebaseUser) {
				updatePhoneFirebaseUserInteractor.execute(
					updatePhoneFirebaseUserInteractorListener,
					firebaseUser,
					phoneAuthCredential!!
				)
			}

			override fun onFail(errorCode: String) {
				view.showLoadingDialog(Constants.HIDE_LOADING)
				view.showUserCreationErrorDialog(errorCode)
			}
		}

	/**
	 * [UpdatePhoneFirebaseUserInteractor.Listener] implementation.
	 */

	private val updatePhoneFirebaseUserInteractorListener =
		object : UpdatePhoneFirebaseUserInteractor.Listener {
			override fun onSuccess() {
				view.showLoadingDialog(Constants.HIDE_LOADING)
				view.showEmailVerificationDialog()
				sendVerificationEmailInteractor.execute(sendVerificationEmailInteractorListener)
			}

			override fun onFail() {
				view.showLoadingDialog(Constants.HIDE_LOADING)
			}
		}

	/**
	 * [SendVerificationEmailInteractor.Listener] implementation.
	 */

	private val sendVerificationEmailInteractorListener =
		object : SendVerificationEmailInteractor.Listener {
			override fun onSuccess() {
				// TODO
			}

			override fun onFail() {
				// TODO
			}
		}
}