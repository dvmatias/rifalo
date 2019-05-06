package com.bluespark.raffleit.screens.signup

import android.os.Handler
import android.support.annotation.NonNull
import com.bluespark.raffleit.common.Constants
import com.bluespark.raffleit.common.model.databaseschemas.CountryCodeSchema
import com.bluespark.raffleit.common.model.objects.Country
import com.bluespark.raffleit.common.mvp.BasePresenterImpl
import java.util.*
import kotlin.collections.ArrayList

class SignUpPresenterImpl(
	view: SignUpContract.View,
	private var signUpFetchCountryCodesInteractor: SignUpFetchCountryCodesInteractor
) : BasePresenterImpl<SignUpContract.View>(),
	SignUpContract.Presenter, SignUpFetchCountryCodesInteractor.Listener {

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
}