package android.marc.com.harrypotterapp.main

import android.marc.com.core.data.CharacterRepository
import androidx.lifecycle.ViewModel

class MainViewModel(characterRepository: CharacterRepository) : ViewModel() {

    val characters = characterRepository.getAllCharacters()
}