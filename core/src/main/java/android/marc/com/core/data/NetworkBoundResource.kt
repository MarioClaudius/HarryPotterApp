package android.marc.com.core.data

import android.marc.com.core.data.source.remote.api.ApiResponse
import android.marc.com.core.utils.AppExecutors
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<RequestType, ResultType>(private val mExecutors: AppExecutors) {

    private var result: Flow<ResourceStatus<ResultType>> = flow {
        emit(ResourceStatus.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(ResourceStatus.Loading())
            when(val apiResponse = createApiCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map { ResourceStatus.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map { ResourceStatus.Success(it) })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(ResourceStatus.Error(apiResponse.errorMessage))
                }
            }
        } else {
            emitAll(loadFromDB().map { ResourceStatus.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createApiCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<ResourceStatus<ResultType>> = result
}