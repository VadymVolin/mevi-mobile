package com.mevi.domain.repository.user.usecase

import com.mevi.domain.model.MeviError
import com.mevi.domain.repository.model.RepositoryException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Base class for the network based use cases
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 6/5/24
 */
abstract class BaseNetworkBasedUseCase<T> {

    protected fun execute(networkOperation: suspend () -> T): Flow<T> = flow {
        emit(networkOperation())
    }.flowOn(Dispatchers.IO)

    protected fun getErrorByException(
        exception: Exception
    ): MeviError =
        when (exception) {
            // thrown if the user account corresponding to email does not exist or has been disabled
            is RepositoryException.AuthInvalidUserException -> MeviError.ERROR_ACCOUNT_DOES_NO_EXIST
            // or if the email or password is invalid
            is RepositoryException.AuthWrongEmailOrPasswordException -> MeviError.ERROR_WRONG_EMAIL_OR_PASSWORD
            // thrown if the email address is malformed
            is RepositoryException.AuthInvalidEmailException -> MeviError.ERROR_INCORRECT_EMAIL
            // thrown if the password is not strong enough
            is RepositoryException.AuthWeakPasswordException -> MeviError.ERROR_WEAK_PASSWORD
            // thrown if there already exists an account with the given email address
            is RepositoryException.AuthUserAccountAlreadyExistsException -> MeviError.ERROR_ACCOUNT_ALREADY_EXISTS
            else -> MeviError.ERROR_UNEXPECTED
        }
}