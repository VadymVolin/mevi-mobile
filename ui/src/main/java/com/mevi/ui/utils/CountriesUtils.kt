package com.mevi.ui.utils

import com.mevi.ui.model.CountryModel
import java.util.ArrayList
import java.util.Locale

object CountriesUtils {
    fun getCountries(): ArrayList<CountryModel> {
        val isoCountryCodes: Array<String> = Locale.getISOCountries()
        val countries: ArrayList<CountryModel> = arrayListOf()
        for (countryCode in isoCountryCodes) {
            val locale = Locale("", countryCode)
            val countryName: String = locale.displayCountry
            val flagOffset = 0x1F1E6
            val asciiOffset = 0x41
            val firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset
            val secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset
            val flag =
                (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))
            val country = CountryModel(countryCode, countryName, flag)
            countries.add(country)
        }
        return countries
    }
}
