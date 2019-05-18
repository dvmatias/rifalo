package com.bluespark.raffleit.common.injection.application

import android.app.Application
import com.bluespark.raffleit.common.utils.managers.PhoneManager
import com.google.gson.Gson
import com.google.i18n.phonenumbers.PhoneNumberUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(application: Application) {

	@Provides
	fun getGson(): Gson = Gson()

	@Singleton
	@Provides
	fun getPhoneNumberUtil(): PhoneNumberUtil = PhoneNumberUtil.getInstance()

	@Singleton
	@Provides
	fun getPhoneManager(phoneNumberUtil: PhoneNumberUtil): PhoneManager = PhoneManager(phoneNumberUtil)

}