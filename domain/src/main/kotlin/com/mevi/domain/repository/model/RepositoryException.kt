package com.mevi.domain.repository.model

/**
 * Base class repository exceptions
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 8/5/24
 */
sealed class RepositoryException(message: String) : Exception(message) {
    data class UnexpectedError(override val message: String = UnexpectedError::class.simpleName.toString()) : RepositoryException(message)
    data class AuthInvalidUserException(override val message: String) : RepositoryException(message)
    data class AuthWrongEmailOrPasswordException(override val message: String) : RepositoryException(message)
    data class AuthInvalidEmailException(override val message: String) : RepositoryException(message)
    data class AuthWeakPasswordException(override val message: String) : RepositoryException(message)
    data class AuthUserAccountAlreadyExistsException(override val message: String) : RepositoryException(message)
}