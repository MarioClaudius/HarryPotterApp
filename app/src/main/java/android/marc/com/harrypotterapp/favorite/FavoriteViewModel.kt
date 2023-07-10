package android.marc.com.harrypotterapp.favorite

import android.marc.com.core.domain.usecase.CharacterUseCase
import androidx.lifecycle.ViewModel

class FavoriteViewModel(characterUseCase: CharacterUseCase) : ViewModel() {

    val favoriteCharacters = characterUseCase.getFavoriteCharacters()
}