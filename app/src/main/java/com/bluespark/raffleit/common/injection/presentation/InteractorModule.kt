package com.bluespark.raffleit.common.injection.presentation

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.bluespark.raffleit.common.utils.managers.InternetConnectivityManager
import com.bluespark.raffleit.screens.signup.RegisterFirebaseUserInteractor
import com.bluespark.raffleit.screens.signup.SignUpFetchCountryCodesInteractor
import com.bluespark.raffleit.screens.signup.fragments.phoneregistration.SendFirebaseOtpInteractor
import com.bluespark.raffleit.screens.signup.fragments.userinfo.ValidateEmailInteractor
import com.bluespark.raffleit.screens.signup.fragments.userinfo.ValidatePasswordConfirmationInteractor
import com.bluespark.raffleit.screens.signup.fragments.userinfo.ValidatePasswordInteractor
import com.bluespark.raffleit.screens.splash.SplashCheckCredentialsInteractor
import com.bluespark.raffleit.screens.splash.SplashCheckNetworkInteractor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

	@Provides
	fun getSplashCheckNetworkInteractor(internetConnectivityManager: InternetConnectivityManager)
			: SplashCheckNetworkInteractor =
		SplashCheckNetworkInteractor(internetConnectivityManager)

	@Provides
	fun getSplashCheckCredentialsInteractor(context: Context): SplashCheckCredentialsInteractor =
		SplashCheckCredentialsInteractor(context)

	@Provides
	fun getSignUpFetchCountryCodesInteractor(rootDatabaseReference: DatabaseReference): SignUpFetchCountryCodesInteractor =
		SignUpFetchCountryCodesInteractor(rootDatabaseReference)

	@Provides
	fun getValidateEmailInteractor(context: Context): ValidateEmailInteractor =
		ValidateEmailInteractor(context)

	@Provides
	fun getValidatePasswordInteractor(context: Context): ValidatePasswordInteractor =
		ValidatePasswordInteractor(context)

	@Provides
	fun getValidatePasswordConfirmationInteractor(context: Context): ValidatePasswordConfirmationInteractor =
		ValidatePasswordConfirmationInteractor(context)

	@Provides
	fun getRegisterFirebaseUserInteractor(
		activity: AppCompatActivity,
		firebaseAuth: FirebaseAuth
	): RegisterFirebaseUserInteractor =
		RegisterFirebaseUserInteractor(activity, firebaseAuth)

	@Provides
	fun getSendFirebaseOtpInteractor(): SendFirebaseOtpInteractor = SendFirebaseOtpInteractor()

}