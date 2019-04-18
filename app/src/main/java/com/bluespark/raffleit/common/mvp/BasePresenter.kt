package com.bluespark.raffleit.common.mvp

interface BasePresenter<V : BaseView> {

	fun bind(view: V?)
	fun unbind()

}