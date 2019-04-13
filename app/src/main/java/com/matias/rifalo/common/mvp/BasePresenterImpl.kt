package com.matias.rifalo.common.mvp

abstract class BasePresenterImpl<V : BaseView> : BasePresenter<V> {

	var view: V? = null

	override fun bind(view: V?) {
		this.view = view
	}

	override fun unbind() {
		this.view = null
	}

}