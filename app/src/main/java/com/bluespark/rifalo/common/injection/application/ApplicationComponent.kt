package com.bluespark.rifalo.common.injection.application

import com.bluespark.rifalo.common.injection.presentation.PresentationComponent
import com.bluespark.rifalo.common.injection.presentation.PresentationModule
import dagger.Component

@Component(
	modules = [
		ApplicationModule::class,
		NetworkingModule::class
	]
)
interface ApplicationComponent {

	fun newPresentationComponent(presentationModule: PresentationModule): PresentationComponent

}