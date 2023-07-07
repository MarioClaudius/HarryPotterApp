package android.marc.com.core.data.source.remote.api

import android.marc.com.core.domain.model.Character
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("characters")
    fun getAllCharacters() : Call<List<Character>>
}