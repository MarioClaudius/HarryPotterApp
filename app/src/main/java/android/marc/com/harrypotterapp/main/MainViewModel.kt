package android.marc.com.harrypotterapp.main

import android.marc.com.core.domain.usecase.CharacterUseCase
import androidx.lifecycle.ViewModel

class MainViewModel(characterUseCase: CharacterUseCase) : ViewModel() {

    val characters = characterUseCase.getAllCharacters()
}