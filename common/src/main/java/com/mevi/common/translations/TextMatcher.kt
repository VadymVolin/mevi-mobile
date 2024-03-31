package com.mevi.common.translations

import java.util.Locale

class TextMatcher(selectedLocale: Locale, private val translations: Map<Locale, Map<String, String>>) {

    private var _selectedLocale: Locale? = null
    val locale: Locale
        get() = _selectedLocale ?: Locale.US

    init {
        _selectedLocale = selectedLocale
    }

    fun changeAppLanguage(newLocale: Locale) {
        if (newLocale != _selectedLocale) {
            _selectedLocale = newLocale
        }
    }

    fun get(key: TextKey): String {
        val languageTranslations = translations[_selectedLocale]
        return languageTranslations?.get(key.name) ?: key.name
    }
}