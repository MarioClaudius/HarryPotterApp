package android.marc.com.core.domain.usecase

import android.marc.com.core.data.ResourceStatus
import android.marc.com.core.domain.model.Character
import androidx.lifecycle.LiveData

interface CharacterUseCase {
    fun getAllCharacters(): LiveData<ResourceStatus<List<Character>>>
    fun getFavoriteCharacters(): LiveData<List<Character>>
    fun setFavoriteCharacter(characterId: String, state: Boolean)
}