package android.marc.com.favorite

import android.marc.com.core.domain.usecase.CharacterUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(characterUseCase: CharacterUseCase) : ViewModel() {
    val favoriteCharacters = characterUseCase.getFavoriteCharacters().asLiveData()
}