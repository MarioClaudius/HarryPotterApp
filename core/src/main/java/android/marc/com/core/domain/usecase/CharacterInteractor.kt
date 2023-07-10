package android.marc.com.core.domain.usecase

import android.marc.com.core.data.ResourceStatus
import android.marc.com.core.domain.model.Character
import android.marc.com.core.domain.repository.ICharacterRepository
import androidx.lifecycle.LiveData

class CharacterInteractor(private val characterRepository: ICharacterRepository): CharacterUseCase {
    override fun getAllCharacters(): LiveData<ResourceStatus<List<Character>>> = characterRepository.getAllCharacters()

    override fun getFavoriteCharacters(): LiveData<List<Character>> = characterRepository.getFavoriteCharacters()

    override fun setFavoriteCharacter(characterId: String, state: Boolean) = characterRepository.setFavoriteCharacter(characterId, state)
}