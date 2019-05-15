package com.bluespark.raffleit.common.injection.presentation

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.bluespark.raffleit.common.utils.managers.FirebaseEmailPasswordManager
import com.bluespark.raffleit.common.utils.managers.FirebaseSignInPhoneManager
import com.bluespark.raffleit.common.utils.managers.FirebaseUserManager
import com.bluespark.raffleit.common.utils.managers.InternetConnectivityManager
import com.bluespark.raffleit.screens.signin.SignInWithEmailAnPasswordInteractor
import com.bluespark.raffleit.screens.signup.fragments.phoneregistration.CreatePhoneAuthCredentialInteractor
import com.bluespark.raffleit.screens.signup.fragments.phoneregistration.SendFirebaseOtpInteractor
import com.bluespark.raffleit.screens.signup.fragments.userinfo.ValidateEmailInteractor
import com.bluespark.raffleit.screens.signup.fragments.userinfo.ValidatePasswordConfirmationInteractor
import com.bluespark.raffleit.screens.signup.fragments.userinfo.ValidatePasswordInteractor
import com.bluespark.raffleit.screens.signup.interactors.CreateUserWithEmailAndPasswordInteractor
import com.bluespark.raffleit.screens.signup.interactors.SendVerificationEmailInteractor
import com.bluespark.raffleit.screens.signup.interactors.SignUpFetchCountryCodesInteractor
import com.bluespark.raffleit.screens.signup.interactors.UpdatePhoneFirebaseUserInteractor
import com.bluespark.raffleit.screens.splash.SplashCheckCredentialsInteractor
import com.bluespark.raffleit.screens.splash.SplashCheckNetworkInteractor
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
		SignUpFetchCountryCodesInteractor(
			rootDatabaseReference
		)

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
		firebaseEmailPasswordManager: FirebaseEmailPasswordManager
	): CreateUserWithEmailAndPasswordInteractor =
		CreateUserWithEmailAndPasswordInteractor(
			activity,
			firebaseEmailPasswordManager
		)

	@Provides
	fun getUpdatePhoneFirebaseUserInteractor(
		activity: AppCompatActivity,
		firebaseUserManager: FirebaseUserManager
	): UpdatePhoneFirebaseUserInteractor =
		UpdatePhoneFirebaseUserInteractor(
			activity,
			firebaseUserManager
		)

	@Provides
	fun getSendFirebaseOtpInteractor(
		firebaseSignInPhoneManager: FirebaseSignInPhoneManager
	): SendFirebaseOtpInteractor =
		SendFirebaseOtpInteractor(firebaseSignInPhoneManager)

	@Provides
	fun getSendVerificationEmailInteractor(
		activity: AppCompatActivity,
		firebaseUserManager: FirebaseUserManager
	): SendVerificationEmailInteractor =
		SendVerificationEmailInteractor(activity, firebaseUserManager)

	@Provides
	fun getCreatePhoneAuthCredentialInteractor(
		firebaseSignInPhoneManager: FirebaseSignInPhoneManager
	): CreatePhoneAuthCredentialInteractor =
		CreatePhoneAuthCredentialInteractor(firebaseSignInPhoneManager)

	@Provides
	fun getSignInWithEmailAnPasswordInteractor(
		activity: AppCompatActivity,
		firebaseEmailPasswordManager: FirebaseEmailPasswordManager
	): SignInWithEmailAnPasswordInteractor =
		SignInWithEmailAnPasswordInteractor(activity, firebaseEmailPasswordManager)

}
