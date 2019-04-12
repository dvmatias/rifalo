package com.matias.rifalo.common.mvp

interface BasePresenter<V : BaseView, T : List<BaseInteractor>> {

	fun bind(view: V)
	fun unbind()

}