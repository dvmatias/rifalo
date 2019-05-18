package com.bluespark.raffleit.screens.choosecountry

import com.bluespark.raffleit.common.model.objects.Country
import com.bluespark.raffleit.common.mvp.BasePresenter
import com.bluespark.raffleit.common.mvp.BaseView

interface ChooseCountryContract {

	interface View : BaseView {
		fun finishWithResult(selectedCountry: Country?)
		fun setData(countryList: List<Country>)
		// UI Feedback.
		fun showEmptyState()
		fun showLoadingDialog(show: Boolean)
		fun showRetryState()
	}

	interface Presenter : BasePresenter<View> {

	}

}