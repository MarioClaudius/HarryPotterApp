package android.marc.com.harrypotterapp.detail

import android.marc.com.core.data.CharacterRepository
import androidx.lifecycle.ViewModel

class DetailViewModel(private val characterRepository: CharacterRepository) : ViewModel() {
    fun setFavoriteCharacter(characterId: String, newState: Boolean) = characterRepository.setFavoriteCharacter(characterId, newState)
}