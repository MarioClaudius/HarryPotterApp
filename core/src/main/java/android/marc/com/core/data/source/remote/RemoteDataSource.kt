package android.marc.com.core.data.source.remote

import android.marc.com.core.data.source.remote.api.ApiResponse
import android.marc.com.core.data.source.remote.api.ApiService
import android.marc.com.core.data.source.remote.response.CharacterResponse
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource private constructor(private val apiService: ApiService){
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(apiService)
            }
    }

    suspend fun getAllCharacters() : Flow<ApiResponse<List<CharacterResponse>>> {
        return flow {
            try {
                val characterList = apiService.getAllCharacters()
                if (characterList.isNotEmpty()) {
                    emit(ApiResponse.Success(characterList))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}