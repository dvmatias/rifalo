package com.bluespark.raffleit.common.utils.managers

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber

@Suppress("unused", "MemberVisibilityCanBePrivate")
class PhoneManager(var phoneNumberUtil: PhoneNumberUtil) {

	fun getFormattedCountryCodeForRegion(nameCode: String): CharSequence? {
		val numericCode = phoneNumberUtil.getCountryCodeForRegion(nameCode)
		return "+$numericCode"
	}

	fun isValidNumber(number: String, nameCode: String): Boolean {
		if (number.length < 2 || nameCode.isEmpty() || !isValidCountryName(nameCode))
			return false
		return phoneNumberUtil.isValidNumber(parse(number, nameCode))
	}

	fun isValidNumber(number: Phonenumber.PhoneNumber): Boolean {
		return phoneNumberUtil.isValidNumber(number)
	}

	fun isValidCountryName(nameCode: String): Boolean =
		phoneNumberUtil.getCountryCodeForRegion(nameCode) != 0

	fun parse(numberToParse: String, defaultRegion: String = "XX"): Phonenumber.PhoneNumber {
		return phoneNumberUtil.parse(numberToParse, defaultRegion)
	}

	fun parse(numberToParse: String, defaultRegion: String, phoneNumber: Phonenumber.PhoneNumber) {
		return phoneNumberUtil.parse(numberToParse, defaultRegion, phoneNumber)
	}

}