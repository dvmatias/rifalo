package com.bluespark.raffleit.screens.choosecountry

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.model.objects.Country
import com.bluespark.raffleit.common.utils.ImageLoader
import com.bluespark.raffleit.common.utils.managers.PhoneManager
import kotlinx.android.synthetic.main.item_country.view.*

class CountryListAdapter(
	private val imageLoader: ImageLoader,
	private val phoneManager: PhoneManager
) :
	RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

	private lateinit var countryList: List<Country>
	private var clickListener: ClickListener? = null

	interface ClickListener {
		fun onItemClick(itemCountry: Country)
	}

	fun setData(countryList: List<Country>) {
		this.countryList = countryList
		notifyDataSetChanged()
	}

	/**
	 * View Holder.
	 */
	class CountryViewHolder(itemView: View) :
		RecyclerView.ViewHolder(itemView) {
		var ivFlag: ImageView = itemView.iv_flag
		var tvCode: TextView = itemView.tv_code
		var tvName: TextView = itemView.tv_name
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
		val itemView = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_country, parent, false)
		return CountryViewHolder(itemView)
	}

	override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
		val itemCountry = countryList[position]
		imageLoader.loadImage(holder.ivFlag, itemCountry.url)
		holder.tvName.tv_name.text = itemCountry.name
		holder.tvCode.tv_code.text =
			phoneManager.getFormattedCountryCodeForRegion(itemCountry.code)
		holder.itemView.setOnClickListener {
			if (clickListener != null) {
				clickListener?.onItemClick(itemCountry)
			} else throw IllegalAccessException("Calling Activity must implement CountryListAdapter.ClickListener interface. Use setListener() method.")
		}
	}

	override fun getItemCount(): Int = if (countryList.isNotEmpty()) countryList.size else 0

	fun setListener(clickListener: ClickListener) {
		this.clickListener = clickListener
	}

}