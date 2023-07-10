package android.marc.com.core.data.source.remote.api

import android.marc.com.core.data.source.remote.response.CharacterResponse
import io.reactivex.Flowable
import retrofit2.http.GET

interface ApiService {
    @GET("characters")
    fun getAllCharacters() : Flowable<List<CharacterResponse>>
}