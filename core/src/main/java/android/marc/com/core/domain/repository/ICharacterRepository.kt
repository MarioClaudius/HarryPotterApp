package android.marc.com.core.domain.repository

import android.marc.com.core.data.ResourceStatus
import android.marc.com.core.domain.model.Character
import androidx.lifecycle.LiveData
import io.reactivex.Flowable

interface ICharacterRepository {

    fun getAllCharacters() : Flowable<ResourceStatus<List<Character>>>

    fun getFavoriteCharacters(): Flowable<List<Character>>

    fun setFavoriteCharacter(characterId: String, state: Boolean)
}