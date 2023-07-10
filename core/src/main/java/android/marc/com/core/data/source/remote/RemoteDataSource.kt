package android.marc.com.core.data.source.remote

import android.marc.com.core.data.source.remote.api.ApiResponse
import android.marc.com.core.data.source.remote.api.ApiService
import android.marc.com.core.data.source.remote.response.CharacterResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService){
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(apiService)
            }
    }

    fun getAllCharacters() : Flowable<ApiResponse<List<CharacterResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<CharacterResponse>>>()
        apiService.getAllCharacters()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val characterList = response
                resultData.onNext(if (characterList.isNotEmpty()) ApiResponse.Success(characterList) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }
}