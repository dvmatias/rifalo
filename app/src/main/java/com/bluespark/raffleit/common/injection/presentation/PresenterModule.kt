package com.bluespark.raffleit.common.injection.presentation

import com.bluespark.raffleit.common.mvp.BaseView
import com.bluespark.raffleit.screens.signin.SignInContract
import com.bluespark.raffleit.screens.signin.SignInPresenterImpl
import com.bluespark.raffleit.screens.signup.fragments.userinfo.SignUpUserInfoContract
import com.bluespark.raffleit.screens.signup.fragments.userinfo.SignUpUserInfoFragment
import com.bluespark.raffleit.screens.signup.fragments.userinfo.SignUpUserInfoPresenterImpl
import com.bluespark.raffleit.screens.splash.SplashCheckCredentialsInteractor
import com.bluespark.raffleit.screens.splash.SplashCheckNetworkInteractor
import com.bluespark.raffleit.screens.splash.SplashContract
import com.bluespark.raffleit.screens.splash.SplashPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

	@Provides
	fun getSplashPresenterImpl(
		view: BaseView,
		checkNetworkInteractor: SplashCheckNetworkInteractor,
		checkCredentialsInteractor: SplashCheckCredentialsInteractor
	): SplashPresenterImpl =
		SplashPresenterImpl(
			view as SplashContract.View,
			checkNetworkInteractor,
			checkCredentialsInteractor
		)

	@Provides
	fun getSignInPresenterImpl(view: BaseView): SignInPresenterImpl =
		SignInPresenterImpl(view as SignInContract.View)

	@Provides
	fun getSignUpUserInfoPresenterImpl(view: BaseView): SignUpUserInfoPresenterImpl =
		SignUpUserInfoPresenterImpl(view as SignUpUserInfoContract.View)

}