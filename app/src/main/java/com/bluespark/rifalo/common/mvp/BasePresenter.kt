package com.bluespark.rifalo.common.mvp

interface BasePresenter<V : BaseView> {

	fun bind(view: V?)
	fun unbind()

}