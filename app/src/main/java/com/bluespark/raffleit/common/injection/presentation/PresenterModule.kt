package com.bluespark.raffleit.common.injection.presentation

import com.bluespark.raffleit.common.mvp.BaseView
import com.bluespark.raffleit.common.utils.managers.PhoneManager
import com.bluespark.raffleit.screens.choosecountry.ChooseCountryContract
import com.bluespark.raffleit.screens.choosecountry.ChooseCountryPresenterImpl
import com.bluespark.raffleit.screens.signin.*
import com.bluespark.raffleit.screens.signup.SignUpContract
import com.bluespark.raffleit.screens.signup.SignUpPresenterImpl
import com.bluespark.raffleit.screens.signup.fragments.phoneregistration.CreatePhoneAuthCredentialInteractor
import com.bluespark.raffleit.screens.signup.fragments.phoneregistration.SendFirebaseOtpInteractor
import com.bluespark.raffleit.screens.signup.fragments.phoneregistration.UserPhoneVerificationContract
import com.bluespark.raffleit.screens.signup.fragments.phoneregistration.UserPhoneVerificationPresenterImpl
import com.bluespark.raffleit.screens.signup.fragments.phonevalidation.UserPhoneValidationContract
import com.bluespark.raffleit.screens.signup.fragments.phonevalidation.UserPhoneValidationPresenterImpl
import com.bluespark.raffleit.screens.signup.fragments.userinfo.*
import com.bluespark.raffleit.screens.signup.interactors.CreateUserWithEmailAndPasswordInteractor
import com.bluespark.raffleit.screens.signup.interactors.SendVerificationEmailInteractor
import com.bluespark.raffleit.screens.signup.interactors.SignUpFetchCountryCodesInteractor
import com.bluespark.raffleit.screens.signup.interactors.UpdatePhoneFirebaseUserInteractor
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
	fun getSignInPresenterImpl(
		view: BaseView,
		validateEmailInteractor: ValidateEmailInteractor,
		validatePasswordInteractor: ValidatePasswordInteractor,
		signInWithEmailAnPasswordInteractor: SignInWithEmailAnPasswordInteractor,
		signInWithGoogleInteractor: SignInWithGoogleInteractor,
		addUserLogedInWithEmailAndPasswordToDatabaseInteractor: AddUserLogedInWithEmailAndPasswordToDatabaseInteractor
	): SignInPresenterImpl =
		SignInPresenterImpl(
			view as SignInContract.View,
			validateEmailInteractor,
			validatePasswordInteractor,
			signInWithEmailAnPasswordInteractor,
			signInWithGoogleInteractor,
			addUserLogedInWithEmailAndPasswordToDatabaseInteractor
		)

	@Provides
	fun getSignUpPresenterImpl(
		view: BaseView,
		signUpFetchCountryCodesInteractor: SignUpFetchCountryCodesInteractor,
		updatePhoneFirebaseUserInteractor: UpdatePhoneFirebaseUserInteractor,
		sendVerificationEmailInteractor: SendVerificationEmailInteractor
	): SignUpPresenterImpl =
		SignUpPresenterImpl(
			view as SignUpContract.View,
			signUpFetchCountryCodesInteractor,
			updatePhoneFirebaseUserInteractor,
			sendVerificationEmailInteractor
		)

	@Provides
	fun getSignUpUserInfoPresenterImpl(
		view: BaseView,
		validateEmailInteractor: ValidateEmailInteractor,
		validatePasswordInteractor: ValidatePasswordInteractor,
		validatePasswordConfirmationInteractor: ValidatePasswordConfirmationInteractor,
		createUserWithEmailAndPasswordInteractor: CreateUserWithEmailAndPasswordInteractor
	): UserEmailPasswordPresenterImpl =
		UserEmailPasswordPresenterImpl(
			view as UserEmailPasswordContract.View,
			validateEmailInteractor,
			validatePasswordInteractor,
			validatePasswordConfirmationInteractor,
			createUserWithEmailAndPasswordInteractor
		)

	@Provides
	fun getUserPhoneValidationPresenterImpl(
		view: BaseView,
		phoneManager: PhoneManager
	): UserPhoneValidationPresenterImpl =
		UserPhoneValidationPresenterImpl(
			view as UserPhoneValidationContract.View,
			phoneManager
		)

	@Provides
	fun getChooseCountryPresenterImpl(view: BaseView): ChooseCountryPresenterImpl =
		ChooseCountryPresenterImpl(view as ChooseCountryContract.View)

	@Provides
	fun getUserPhoneVerificationPresenterImpl(
		view: BaseView,
		sendFirebaseOtpInteractor: SendFirebaseOtpInteractor,
		createPhoneAuthCredentialInteractor: CreatePhoneAuthCredentialInteractor
	): UserPhoneVerificationPresenterImpl =
		UserPhoneVerificationPresenterImpl(
			view as UserPhoneVerificationContract.View,
			sendFirebaseOtpInteractor,
			createPhoneAuthCredentialInteractor
		)

}