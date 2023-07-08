package android.marc.com.core.data

import android.marc.com.core.data.source.remote.api.ApiResponse
import android.marc.com.core.utils.AppExecutors
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

abstract class NetworkBoundResource<RequestType, ResultType>(private val mExecutors: AppExecutors) {

    private val result = MediatorLiveData<ResourceStatus<ResultType>>()

    init {
        result.value = ResourceStatus.Loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = ResourceStatus.Success(newData)
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createApiCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createApiCall()

        result.addSource(dbSource) { newData ->
            result.value = ResourceStatus.Loading(newData)
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiResponse.Success ->
                    mExecutors.diskIO().execute {
                        saveCallResult(response.data)
                        mExecutors.mainThread().execute {
                            result.addSource(loadFromDB()) { newData ->
                                result.value = ResourceStatus.Success(newData)
                            }
                        }
                    }
                is ApiResponse.Empty -> mExecutors.mainThread().execute {
                    result.addSource(loadFromDB()) { newData ->
                        result.value = ResourceStatus.Success(newData)
                    }
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.value = ResourceStatus.Error(response.errorMessage, newData)
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<ResourceStatus<ResultType>> = result
}