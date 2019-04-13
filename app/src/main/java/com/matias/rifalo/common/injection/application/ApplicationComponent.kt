package com.matias.rifalo.common.injection.application

import com.matias.rifalo.common.injection.presentation.PresentationComponent
import com.matias.rifalo.common.injection.presentation.PresentationModule
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