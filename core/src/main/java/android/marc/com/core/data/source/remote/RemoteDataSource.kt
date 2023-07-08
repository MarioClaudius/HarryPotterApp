package android.marc.com.core.data.source.remote

import android.marc.com.core.data.source.remote.api.ApiResponse
import android.marc.com.core.data.source.remote.api.ApiService
import android.marc.com.core.data.source.remote.response.CharacterResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    fun getAllCharacters() : LiveData<ApiResponse<List<CharacterResponse>>> {
        val resultData = MutableLiveData<ApiResponse<List<CharacterResponse>>>()

        apiService.getAllCharacters().enqueue(object : Callback<List<CharacterResponse>>{
            override fun onResponse(
                call: Call<List<CharacterResponse>>,
                response: Response<List<CharacterResponse>>
            ) {
                val characterList = response.body()
                if (characterList == null || characterList.isEmpty()) {
                    resultData.value = ApiResponse.Empty
                } else {
                    resultData.value = ApiResponse.Success(characterList)
                }
            }

            override fun onFailure(call: Call<List<CharacterResponse>>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.toString())
                Log.e("RemoteDataSource", t.toString())
            }
        })
        return resultData
    }
}