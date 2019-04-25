package com.bluespark.raffleit.common.injection.presentation

import android.support.v4.app.FragmentManager
import com.bluespark.raffleit.screens.signup.SignUpFragmentAdapter
import dagger.Module
import dagger.Provides

@Module
class AdaptersModule {

	@Provides
	fun getSignUpFragmentAdapter(fragmentManager: FragmentManager): SignUpFragmentAdapter =
		SignUpFragmentAdapter(fragmentManager)

}