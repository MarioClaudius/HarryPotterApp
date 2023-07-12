package android.marc.com.harrypotterapp.detail

import android.marc.com.core.domain.usecase.CharacterUseCase
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val characterUseCase: CharacterUseCase) : ViewModel() {
    fun setFavoriteCharacter(characterId: String, newState: Boolean) = characterUseCase.setFavoriteCharacter(characterId, newState)
}