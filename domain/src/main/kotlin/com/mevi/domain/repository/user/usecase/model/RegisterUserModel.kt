package com.mevi.domain.repository.user.usecase.model

data class RegisterUserModel(
    val email: String? = null,
    val name: String? = null,
    val password: String? = null,
    val country: Country? = null,
    val gender: Gender? = null
)
