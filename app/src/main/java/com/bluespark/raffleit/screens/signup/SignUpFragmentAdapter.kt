package com.bluespark.raffleit.screens.signup

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.bluespark.raffleit.screens.signup.fragments.phoneregistration.UserPhoneRegistrationFragment
import com.bluespark.raffleit.screens.signup.fragments.phonevalidation.UserPhoneValidationFragment
import com.bluespark.raffleit.screens.signup.fragments.userinfo.UserInfoFragment

class SignUpFragmentAdapter(fragmentManager: FragmentManager) :
	FragmentPagerAdapter(fragmentManager) {

	private val fragmentList = ArrayList<Fragment>()
	private val fragmentTitleList = ArrayList<String>()

	init {
		fragmentList.add(UserInfoFragment.newInstance())
		fragmentTitleList.add(UserInfoFragment.TAG)
		fragmentList.add(UserPhoneValidationFragment.newInstance())
		fragmentTitleList.add(UserPhoneValidationFragment.TAG)
		fragmentList.add(UserPhoneRegistrationFragment.newInstance())
		fragmentTitleList.add(UserPhoneRegistrationFragment.TAG)
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