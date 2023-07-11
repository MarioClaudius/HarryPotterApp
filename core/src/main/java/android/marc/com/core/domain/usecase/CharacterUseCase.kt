package android.marc.com.core.domain.usecase

import android.marc.com.core.data.ResourceStatus
import android.marc.com.core.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterUseCase {
    fun getAllCharacters(): Flow<ResourceStatus<List<Character>>>
    fun getFavoriteCharacters(): Flow<List<Character>>
    fun setFavoriteCharacter(characterId: String, state: Boolean)
}