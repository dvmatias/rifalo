package com.bluespark.raffleit.screens.singin

import com.bluespark.raffleit.common.mvp.BasePresenterImpl

class SignInPresenterImpl(view: SignInContract.View?) : BasePresenterImpl<SignInContract.View>(),
	SignInContract.Presenter {

	init {
		bind(view)
	}

	override fun signInGoogle() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun signInFacebook() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun login() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}