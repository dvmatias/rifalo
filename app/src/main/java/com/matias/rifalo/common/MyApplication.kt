package com.matias.rifalo.common

import android.app.Application

/**
 * Custom Application class.
 *
 * This class shall be used to:
 *   1- Access to variables across the Application.
 *   2- Trigger actions that needs to be up before any Activity or Service.
 *   3- Use onConfigurationChanged() to catch changes in configurations.
 *   4- Instantiate Dagger component at Application level.
 */

class MyApplication : Application() {

	override fun onCreate() {
		super.onCreate()

//		setup() TODO setup applicationComponent.
	}
	
}