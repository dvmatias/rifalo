package com.matias.rifalo.screens.splash

import com.matias.rifalo.common.mvp.BasePresenterImpl

class SplashPresenterImpl(view: SplashContract.View) : BasePresenterImpl<SplashContract.View>() {

	init {
		super.bind(view)
	}

}