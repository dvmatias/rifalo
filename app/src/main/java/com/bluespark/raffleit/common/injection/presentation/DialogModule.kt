package com.bluespark.raffleit.common.injection.presentation

import android.support.v4.app.FragmentManager
import com.bluespark.raffleit.common.utils.managers.DialogsManager
import com.bluespark.raffleit.common.views.dialogs.LoadingDialogFragment
import com.bluespark.raffleit.common.views.dialogs.WarningDialogFragmentImpl
import dagger.Module
import dagger.Provides

@Module
class DialogModule {

	@Provides
	fun getDialogsManager(fragmentManager: FragmentManager): DialogsManager =
		DialogsManager(fragmentManager)

	@Provides
	fun getLoadingDialogFragment(): LoadingDialogFragment = LoadingDialogFragment.newInstance()

	@Provides
	fun getWarningDialogFragmentImpl(): WarningDialogFragmentImpl =
		WarningDialogFragmentImpl.newInstance()

}