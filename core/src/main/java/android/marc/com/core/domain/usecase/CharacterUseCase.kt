package android.marc.com.core.domain.usecase

import android.marc.com.core.data.ResourceStatus
import android.marc.com.core.domain.model.Character
import io.reactivex.Flowable

interface CharacterUseCase {
    fun getAllCharacters(): Flowable<ResourceStatus<List<Character>>>
    fun getFavoriteCharacters(): Flowable<List<Character>>
    fun setFavoriteCharacter(characterId: String, state: Boolean)
}