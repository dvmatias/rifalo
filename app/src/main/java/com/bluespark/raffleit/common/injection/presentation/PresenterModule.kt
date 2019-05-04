package com.bluespark.raffleit.common.injection.presentation

import com.bluespark.raffleit.common.mvp.BaseView
import com.bluespark.raffleit.common.utils.managers.PhoneManager
import com.bluespark.raffleit.screens.choosecountry.ChooseCountryContract
import com.bluespark.raffleit.screens.choosecountry.ChooseCountryPresenterImpl
import com.bluespark.raffleit.screens.signin.SignInContract
import com.bluespark.raffleit.screens.signin.SignInPresenterImpl
import com.bluespark.raffleit.screens.signup.SignUpContract
import com.bluespark.raffleit.screens.signup.SignUpFetchCountryCodesInteractor
import com.bluespark.raffleit.screens.signup.SignUpPresenterImpl
import com.bluespark.raffleit.screens.signup.fragments.phonevalidation.UserPhoneValidationContract
import com.bluespark.raffleit.screens.signup.fragments.phonevalidation.UserPhoneValidationPresenterImpl
import com.bluespark.raffleit.screens.signup.fragments.userinfo.*
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
	fun getSignUpPresenterImpl(
		view: BaseView,
		signUpFetchCountryCodesInteractor: SignUpFetchCountryCodesInteractor
	): SignUpPresenterImpl =
		SignUpPresenterImpl(view as SignUpContract.View, signUpFetchCountryCodesInteractor)

	@Provides
	fun getSignUpUserInfoPresenterImpl(
		view: BaseView,
		validateEmailInteractor: ValidateEmailInteractor,
		validatePasswordInteractor: ValidatePasswordInteractor,
		validatePasswordConfirmationInteractor: ValidatePasswordConfirmationInteractor
	): UserInfoPresenterImpl =
		UserInfoPresenterImpl(
			view as UserInfoContract.View,
			validateEmailInteractor,
			validatePasswordInteractor,
			validatePasswordConfirmationInteractor
		)

	@Provides
	fun getSignUpUserPhoneValidationPresenterImpl(
		view: BaseView,
		checkNetworkInteractor: SplashCheckNetworkInteractor,
		phoneManager: PhoneManager
	): UserPhoneValidationPresenterImpl =
		UserPhoneValidationPresenterImpl(
			view as UserPhoneValidationContract.View,
			checkNetworkInteractor,
			phoneManager
		)

	@Provides
	fun getChooseCountryPresenterImpl(view: BaseView): ChooseCountryPresenterImpl =
		ChooseCountryPresenterImpl(view as ChooseCountryContract.View)

}