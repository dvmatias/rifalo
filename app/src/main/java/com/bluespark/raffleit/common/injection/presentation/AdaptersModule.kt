package com.bluespark.raffleit.common.injection.presentation

import android.support.v4.app.FragmentManager
import com.bluespark.raffleit.common.utils.ImageLoader
import com.bluespark.raffleit.common.utils.managers.PhoneManager
import com.bluespark.raffleit.screens.choosecountry.CountryListAdapter
import com.bluespark.raffleit.screens.signup.SignUpFragmentAdapter
import dagger.Module
import dagger.Provides

@Module
class AdaptersModule {

	@Provides
	fun getSignUpFragmentAdapter(fragmentManager: FragmentManager): SignUpFragmentAdapter =
		SignUpFragmentAdapter(fragmentManager)

	@Provides
	fun getCountryListAdapter(
		imageLoader: ImageLoader,
		phoneManager: PhoneManager
	): CountryListAdapter = CountryListAdapter(imageLoader, phoneManager)

}