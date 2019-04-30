package com.bluespark.raffleit.screens.signup.fragments.phonevalidation

import com.bluespark.raffleit.common.mvp.BasePresenter
import com.bluespark.raffleit.common.mvp.BaseView

interface UserPhoneValidationContract {

	interface View : BaseView {
		// UI Feedback
		fun enableTermsAndConditions(isEnabled: Boolean)
	}

	interface Presenter : BasePresenter<View> {

	}
}
