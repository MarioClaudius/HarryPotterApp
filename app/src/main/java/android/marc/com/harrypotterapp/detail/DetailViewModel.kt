package android.marc.com.harrypotterapp.detail

import android.marc.com.core.domain.usecase.CharacterUseCase
import androidx.lifecycle.ViewModel

class DetailViewModel(private val characterUseCase: CharacterUseCase) : ViewModel() {
    fun setFavoriteCharacter(characterId: String, newState: Boolean) = characterUseCase.setFavoriteCharacter(characterId, newState)
}