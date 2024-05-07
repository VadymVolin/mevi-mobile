package com.mevi.domain.repository.auth.usecase

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
}