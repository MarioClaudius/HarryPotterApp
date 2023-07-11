package android.marc.com.core.domain.usecase

import android.marc.com.core.data.ResourceStatus
import android.marc.com.core.domain.model.Character
import android.marc.com.core.domain.repository.ICharacterRepository
import kotlinx.coroutines.flow.Flow

class CharacterInteractor(private val characterRepository: ICharacterRepository): CharacterUseCase {
    override fun getAllCharacters(): Flow<ResourceStatus<List<Character>>> = characterRepository.getAllCharacters()

    override fun getFavoriteCharacters(): Flow<List<Character>> = characterRepository.getFavoriteCharacters()

    override fun setFavoriteCharacter(characterId: String, state: Boolean) = characterRepository.setFavoriteCharacter(characterId, state)
}