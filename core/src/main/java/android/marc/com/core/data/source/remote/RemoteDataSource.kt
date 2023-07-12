package android.marc.com.core.data.source.remote

import android.marc.com.core.data.source.remote.api.ApiResponse
import android.marc.com.core.data.source.remote.api.ApiService
import android.marc.com.core.data.source.remote.response.CharacterResponse
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService){
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