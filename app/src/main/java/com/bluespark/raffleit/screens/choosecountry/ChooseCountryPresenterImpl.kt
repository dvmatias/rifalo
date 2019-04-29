package com.bluespark.raffleit.screens.choosecountry

import com.bluespark.raffleit.common.mvp.BasePresenterImpl

class ChooseCountryPresenterImpl(
	view: ChooseCountryContract.View
) : BasePresenterImpl<ChooseCountryContract.View>(),
	ChooseCountryContract.Presenter {

	init {
		bind(view)
	}

}