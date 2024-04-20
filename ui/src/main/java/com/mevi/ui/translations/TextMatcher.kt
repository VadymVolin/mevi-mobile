package com.mevi.ui.translations

import java.util.Locale

/**
 * UI component for getting translated text by key([TextKey])
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 20/04/24
 */
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

    operator fun get(key: TextKey): String {
        val languageTranslations = translations[_selectedLocale]
        return languageTranslations?.get(key.name) ?: key.name
    }
}