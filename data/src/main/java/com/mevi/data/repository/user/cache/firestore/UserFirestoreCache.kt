package com.mevi.data.repository.user.cache.firestore

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mevi.data.repository.user.api.model.UserDto
import com.mevi.domain.repository.user.usecase.model.RegisterUserModel
import kotlinx.coroutines.tasks.await

/**
 * Cache client that works with Firebase Firestore
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 3/8/24
 */
class UserFirestoreCache {
    private val firestoreClient by lazy { Firebase.firestore }

    suspend fun createUser(registerUserModel: RegisterUserModel, userDto: UserDto) {
        val email = userDto.email ?: throw IllegalArgumentException("Cannot create user, Email is null")
        firestoreClient.collection(FirestoreKey.Collection.USER.value)
            .document(userDto.email)
            .set(
                mapOf(
                    FirestoreKey.User.AVATAR.name to userDto.photoUrl,
                    FirestoreKey.User.COUNTRY.name to registerUserModel.country?.isoCode,
                    FirestoreKey.User.EMAIL.name to email,
                    FirestoreKey.User.GENDER.name to registerUserModel.gender?.type,
                    FirestoreKey.User.IS_EMAIL_VERIFIED.name to userDto.isEmailVerified,
                    FirestoreKey.User.NAME.name to registerUserModel.name,
                    FirestoreKey.User.PASSWORD.name to registerUserModel.password,
                    FirestoreKey.User.PHONE_NUMBER.name to userDto.phoneNumber,
                    FirestoreKey.User.PROVIDER_ID.name to userDto.provider.name,
                )
            )
            .await()
    }
}