package android.marc.com.core.data

import android.marc.com.core.data.source.remote.api.ApiResponse
import android.marc.com.core.utils.AppExecutors
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

abstract class NetworkBoundResource<RequestType, ResultType>(private val mExecutors: AppExecutors) {

    private val result = PublishSubject.create<ResourceStatus<ResultType>>()
    private val mCompositeDisposable = CompositeDisposable()

    init {
        @Suppress("LeakingThis")
        val dbSource = loadFromDB()
        val db = dbSource
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe{ data ->
                dbSource.unsubscribeOn(Schedulers.io())
                if (shouldFetch(data)) {
                    fetchFromNetwork()
                } else {
                    result.onNext(ResourceStatus.Success(data))
                }
            }
        mCompositeDisposable.add(db)
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flowable<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createApiCall(): Flowable<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork() {
        val apiResponse = createApiCall()

        result.onNext(ResourceStatus.Loading(null))
        val response = apiResponse
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .doOnComplete{
                mCompositeDisposable.dispose()
            }
            .subscribe{ response ->
                when(response) {
                    is ApiResponse.Success -> {
                        saveCallResult(response.data)
                        val dbSource = loadFromDB()
                        dbSource.subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .take(1)
                            .subscribe { data ->
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(ResourceStatus.Success(data))
                            }
                    }
                    is ApiResponse.Empty -> {
                        val dbSource = loadFromDB()
                        dbSource.subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .take(1)
                            .subscribe { data ->
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(ResourceStatus.Success(data))
                            }
                    }
                    is ApiResponse.Error -> {
                        onFetchFailed()
                        result.onNext(ResourceStatus.Error(response.errorMessage, null))
                    }
                }
            }
        mCompositeDisposable.add(response)
    }

    fun asFlowable(): Flowable<ResourceStatus<ResultType>> = result.toFlowable(BackpressureStrategy.BUFFER)
}