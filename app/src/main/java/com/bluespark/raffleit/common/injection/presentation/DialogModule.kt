package com.bluespark.raffleit.common.injection.presentation

import android.support.v4.app.FragmentManager
import com.bluespark.raffleit.common.utils.managers.DialogsManager
import com.bluespark.raffleit.common.views.LoadingDialogFragment
import dagger.Module
import dagger.Provides

@Module
class DialogModule {

	@Provides
	fun getDialogsManager(fragmentManager: FragmentManager): DialogsManager =
		DialogsManager(fragmentManager)

	@Provides
	fun getLoadingDialogFragment(): LoadingDialogFragment = LoadingDialogFragment.newInstance()

}