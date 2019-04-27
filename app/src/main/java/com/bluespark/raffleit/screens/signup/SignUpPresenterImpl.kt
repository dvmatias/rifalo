package com.bluespark.raffleit.screens.signup

import android.util.Log
import com.bluespark.raffleit.common.Constants
import com.bluespark.raffleit.common.model.objects.SignUpUser
import com.bluespark.raffleit.common.mvp.BasePresenterImpl

class SignUpPresenterImpl(
	view: SignUpContract.View,
	private var signUpFetchCountryCodesInteractor: SignUpFetchCountryCodesInteractor
) : BasePresenterImpl<SignUpContract.View>(),
	SignUpContract.Presenter, SignUpFetchCountryCodesInteractor.Listener {

	private var signUpUser: SignUpUser? = null

	init {
		bind(view)
	}

	override fun fetchCountryCodes() {
		view?.showLoading(Constants.SHOW_LOADING)
		signUpFetchCountryCodesInteractor.execute(this)
	}

	override fun setSignUpUser(signUpUser: SignUpUser) {
		this.signUpUser = signUpUser
	}

	/**
	 * [SignUpFetchCountryCodesInteractor.Listener] implementation.
	 */

	override fun onSuccess() {
		Log.d("SignUpPresenterImpl", "onSuccess() signUpFetchCountryCodesInteractor")
		view?.showLoading(Constants.HIDE_LOADING)
	}

	override fun onFail() {
		Log.d("SignUpPresenterImpl", "onFail() signUpFetchCountryCodesInteractor")
		view?.showLoading(Constants.HIDE_LOADING)
	}
}