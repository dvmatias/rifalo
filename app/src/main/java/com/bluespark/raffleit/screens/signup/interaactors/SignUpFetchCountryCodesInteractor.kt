package com.bluespark.raffleit.screens.signup.interaactors

import android.support.annotation.NonNull
import com.bluespark.raffleit.common.model.databaseschemas.CountryCodeSchema
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class SignUpFetchCountryCodesInteractor(private var rootDatabaseReference: DatabaseReference) {

	interface Listener {
		fun onSuccess(@NonNull countryCodeScheme: CountryCodeSchema)
		fun onFail()
	}

	fun execute(listener: Listener) {
		val countryCodeDatabaseReference = rootDatabaseReference.child("country_code")
		countryCodeDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
			override fun onCancelled(databaseError: DatabaseError) {
				listener.onFail()
			}

			override fun onDataChange(dataSnapshot: DataSnapshot) {
				val countryCodeScheme = CountryCodeSchema()

				try {
					for (ds in dataSnapshot.children) {

						for (dsLanguage in ds.children) {
							val languageSchema = CountryCodeSchema.LanguageSchema()
							languageSchema.code = dsLanguage.key

							for (dsCountry in dsLanguage.children) {
								val countryScheme = dsCountry.getValue(CountryCodeSchema.CountrySchema::class.java)
								if (countryScheme != null)
									languageSchema.countrySchemaList.add(countryScheme)
							}

							countryCodeScheme.language.add(languageSchema)
						}
					}
				} catch (e: Exception) {
					listener.onFail()
					e.printStackTrace()
				} finally {
					if (!countryCodeScheme.language.isEmpty()) {
						listener.onSuccess(countryCodeScheme)
					}
				}
			}

		})
	}

	companion object {
		@Suppress("unused")
		private val TAG = SignUpFetchCountryCodesInteractor::class.java.simpleName
	}

}