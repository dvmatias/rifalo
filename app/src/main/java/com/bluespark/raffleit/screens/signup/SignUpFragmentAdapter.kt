package com.bluespark.raffleit.screens.signup

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SignUpFragmentAdapter(fragmentManager: FragmentManager) :
	FragmentPagerAdapter(fragmentManager) {

	private val fragmentList = ArrayList<Fragment>()
	private val fragmentTitleList = ArrayList<String>()

	fun addFragment(fragment: Fragment, title: String) {
		fragmentList.add(fragment)
		fragmentTitleList.add(title)
	}

	override fun getItem(position: Int): Fragment {
		return fragmentList[position]
	}

	/**
	 * If you want to only show icons, return null from this method.
	 * @param position
	 * @return
	 */
	override fun getPageTitle(position: Int): CharSequence? {
		return fragmentTitleList[position]
	}

	override fun getCount(): Int {
		return fragmentList.size
	}
}