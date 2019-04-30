package com.bluespark.raffleit.screens.choosecountry

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.Constants.Companion.EXTRA_KEY_COUNTRY_LIST
import com.bluespark.raffleit.common.Constants.Companion.EXTRA_KEY_SELECTED_COUNTRY
import com.bluespark.raffleit.common.model.objects.Country
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_choose_country.*
import javax.inject.Inject
import com.google.gson.reflect.TypeToken



class ChooseCountryActivity : BaseActivityImpl(),
	ChooseCountryContract.View,
	CountryListAdapter.ClickListener {

	@Inject
	lateinit var presenter: ChooseCountryPresenterImpl

	@Inject
	lateinit var adapter: CountryListAdapter

	@Inject
	lateinit var gson: Gson

	private lateinit var selectedCountry: Country

	private lateinit var countryList: List<Country>

	companion object {
		// Class tag.
		private val TAG = ChooseCountryActivity::class.java.simpleName
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_choose_country)
		getPresentationComponent().inject(this)

		getExtras()
		setupActionBar()
		setupRecyclerView()
	}

	override fun onResume() {
		super.onResume()
		setData(countryList)
	}

	private fun setupActionBar() {
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.title = "Choose Country"
	}

	private fun setupRecyclerView() {
		adapter.setListener(this)
		rv_country.layoutManager = LinearLayoutManager(this)
		rv_country.adapter = adapter
	}

	private fun getExtras() {
		// get selected country
		selectedCountry = gson.fromJson(
			intent.getStringExtra(EXTRA_KEY_SELECTED_COUNTRY),
			Country::class.java
		)
		// get list of country objects
		val countryListType = object : TypeToken<ArrayList<Country>>() {}.type
		countryList = gson.fromJson(
			intent.getStringExtra(EXTRA_KEY_COUNTRY_LIST),
			countryListType
		)
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		return when (item?.itemId) {
			R.id.homeAsUp -> {
				finish()
				true
			}
			else -> super.onOptionsItemSelected(item)
		}
	}

	override fun onSupportNavigateUp(): Boolean {
		finishWithResult(null)
		return true
	}

	/*
	 * MVP - [ChooseCountryContract.View] interface implementation.
	 */

	override fun finishWithResult(selectedCountry: Country?) {
		if (null != selectedCountry) {
			val intent = Intent()
			intent.putExtra(
				EXTRA_KEY_SELECTED_COUNTRY,
				gson.toJson(selectedCountry)
			)
			setResult(Activity.RESULT_OK, intent)
		} else {
			setResult(Activity.RESULT_CANCELED)
		}
		finish()
	}

	override fun setData(@NonNull countryList: List<Country>) {
		adapter.setData(countryList)
	}

	override fun showEmptyState() {
		Log.d(TAG, "*** MABEL showEmptyState()")
		// TODO
	}

	override fun showLoading(show: Boolean) {
		if (show) {
			rv_country.visibility = View.GONE
//			progress.visibility = View.VISIBLE TODO
		} else {
//			progress.visibility = View.GONE TODO
			rv_country.visibility = View.VISIBLE
		}
	}

	override fun showRetryState() {
		Log.d(TAG, "*** MABEL showRetryState()")
		// TODO
	}

	/*
	 * [CountryListAdapter.ClickListener] interface implementation.
	 */

	override fun onItemClick(itemCountry: Country) {
		selectedCountry = itemCountry
		finishWithResult(itemCountry)
	}
}
