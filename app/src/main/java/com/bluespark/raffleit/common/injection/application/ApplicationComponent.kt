package com.bluespark.raffleit.common.injection.application

import com.bluespark.raffleit.common.injection.presentation.PresentationComponent
import com.bluespark.raffleit.common.injection.presentation.PresentationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
	modules = [
		ApplicationModule::class,
		NetworkingModule::class
	]
)
interface ApplicationComponent {

	fun newPresentationComponent(presentationModule: PresentationModule): PresentationComponent

}