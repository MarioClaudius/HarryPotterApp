package android.marc.com.core.data.source.remote.api

import android.marc.com.core.data.source.remote.response.CharacterResponse
import retrofit2.http.GET

interface ApiService {
    @GET("characters")
    suspend fun getAllCharacters() : List<CharacterResponse>
}