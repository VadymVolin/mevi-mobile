package com.mevi.data.repository.user.cache.firestore

/**
 * Firebase Firestore field keys
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 3/8/24
 */
sealed class FirestoreKey {

    enum class Collection(val value: String) {
        USER("user")
    }

    /**
     * User collection related keys
     */
    enum class User {
        AVATAR, COUNTRY, GENDER, EMAIL, IS_EMAIL_VERIFIED, NAME, PASSWORD, PHONE_NUMBER, PROVIDER_ID
    }
}