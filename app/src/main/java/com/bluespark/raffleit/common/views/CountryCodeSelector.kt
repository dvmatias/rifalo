package com.bluespark.raffleit.common.views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.model.objects.Country
import com.bluespark.raffleit.common.utils.ImageLoader
import com.bluespark.raffleit.common.utils.managers.PhoneManager
import kotlinx.android.synthetic.main.view_country_code_selector.view.*

class CountryCodeSelector(context: Context, attrs: AttributeSet) :
	LinearLayout(context, attrs), View.OnClickListener, TextWatcher {

	private lateinit var listener: Listener
	var phoneNumber: String
	var country: Country = Country("XX", "+0", "", "")


	init {
		inflate(context, R.layout.view_country_code_selector, this)

		btn_country.setOnClickListener(this)
		et_phone.addTextChangedListener(this)
		hideError()

		phoneNumber = ""

		setClickListener()
	}

	override fun afterTextChanged(s: Editable?) {}

	override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

	override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
		hideError()
		phoneNumber = if (!s.isNullOrBlank() && s.isNotEmpty()) {
			listener.onPhoneNotEmpty()
			s.toString()
		} else {
			listener.onPhoneEmpty()
			""
		}
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			btn_country.id -> {
				listener.onCountryClick()
				hideError()
			}
		}
	}

	interface Listener {
		fun onCountryClick()
		fun onPhoneEmpty()
		fun onPhoneNotEmpty()
	}

	private fun setClickListener() {
		if (context is Listener)
			this.listener = this.context as Listener
		else
			throw IllegalAccessException("Calling Activity must implement CountryCodeSelector.Listener interface.")
	}

	fun showCountryInfo(
		selectedCountry: Country,
		imageLoader: ImageLoader,
		phoneManager: PhoneManager
	) {
		country = selectedCountry
		imageLoader.loadImage(iv_flag, country.url)
		tv_code.text = phoneManager.getFormattedCountryCodeForRegion(country.code)
	}

	fun showError(errorMessage: String) {
		tv_error.text = errorMessage
		tv_error.visibility = View.VISIBLE
		animateError()
	}

	private fun animateError() {
		val animShake = AnimationUtils.loadAnimation(context, R.anim.shake_inline_error)
		tv_error.startAnimation(animShake)
	}

	fun hideError() {
		tv_error.visibility = View.INVISIBLE
	}

}