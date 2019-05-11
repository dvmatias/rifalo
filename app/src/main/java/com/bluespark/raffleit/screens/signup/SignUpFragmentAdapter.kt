package com.bluespark.raffleit.screens.signup

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.bluespark.raffleit.screens.signup.fragments.phoneregistration.UserPhoneVerificationFragment
import com.bluespark.raffleit.screens.signup.fragments.phonevalidation.UserPhoneValidationFragment
import com.bluespark.raffleit.screens.signup.fragments.userinfo.UserEmailPasswordFragment

class SignUpFragmentAdapter(fragmentManager: FragmentManager) :
	FragmentPagerAdapter(fragmentManager) {

	private val fragmentList = ArrayList<Fragment>()
	private val fragmentTitleList = ArrayList<String>()

	init {
		fragmentList.add(UserEmailPasswordFragment.newInstance())
		fragmentTitleList.add(UserEmailPasswordFragment.TAG)
		fragmentList.add(UserPhoneValidationFragment.newInstance())
		fragmentTitleList.add(UserPhoneValidationFragment.TAG)
		fragmentList.add(UserPhoneVerificationFragment.newInstance())
		fragmentTitleList.add(UserPhoneVerificationFragment.TAG)
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