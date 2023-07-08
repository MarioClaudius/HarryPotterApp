package android.marc.com.core.data.source.remote.api

import android.marc.com.core.data.source.remote.response.CharacterResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("characters")
    fun getAllCharacters() : Call<List<CharacterResponse>>
}