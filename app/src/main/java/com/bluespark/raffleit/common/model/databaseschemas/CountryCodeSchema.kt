package com.bluespark.raffleit.common.model.databaseschemas

class CountryCodeSchema {
	// Array of languages
	var language: ArrayList<LanguageSchema> = ArrayList()

	/**
	 * TODO desc.
	 */
	class LanguageSchema {
		// Language code name (i.e. "en", "es")
		var code: String? = null
		// Array of country objects
		var countrySchemaList: ArrayList<CountrySchema> = ArrayList()
	}

	/**
	 * TODO desc.
	 */
	class CountrySchema {
		// Country code name (i.e. "AR")
		var code: String? = null
		// Country dial code number (i.e. "+54")
		var dial_code: String? = null
		// Country name (i.e. "Argentina")
		var name: String? = null
		// Country flag image URL (i.e. "http://.../.../...png")
		var url: String? = null
	}
}