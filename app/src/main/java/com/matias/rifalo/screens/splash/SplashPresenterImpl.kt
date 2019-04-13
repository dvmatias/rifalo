package com.matias.rifalo.screens.splash

import android.util.Log
import com.matias.rifalo.common.mvp.BasePresenterImpl

class SplashPresenterImpl(
	view: SplashContract.View,
	private var checkNetworkInteractor: SplashCheckNetworkInteractor
) : BasePresenterImpl<SplashContract.View>(),
	SplashContract.Presenter {

	init {
		super.bind(view)
	}

	/**
	 * [SplashContract.Presenter] interface implementation.
	 */

	override fun actionCheckInternetConnection() {
		Log.d(SplashPresenterImpl::class.java.simpleName, "MABEL actionCheckInternetConnection()")
		checkNetworkInteractor.execute(networkListener)
	}

	override fun actionCheckCredentials() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}


	/**
	 * [SplashCheckNetworkInteractor.Listener] interface implementation.
	 */
	private var networkListener = object : SplashCheckNetworkInteractor.Listener {
		override fun onInternetConnected() {
			Log.d(SplashPresenterImpl::class.java.simpleName, "MABEL Internet Connected!")
		}

		override fun onInternetNotConnected() {
			Log.d(SplashPresenterImpl::class.java.simpleName, "MABEL Internet Not Connected!")
		}

	}
}