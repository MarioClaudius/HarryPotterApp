package android.marc.com.harrypotterapp.favorite

import android.marc.com.core.data.CharacterRepository
import androidx.lifecycle.ViewModel

class FavoriteViewModel(characterRepository: CharacterRepository) : ViewModel() {

    val favoriteCharacters = characterRepository.getFavoriteCharacters()
}