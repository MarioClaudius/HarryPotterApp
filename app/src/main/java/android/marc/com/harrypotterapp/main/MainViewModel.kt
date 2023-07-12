package android.marc.com.harrypotterapp.main

import android.marc.com.core.domain.usecase.CharacterUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import javax.inject.Inject

class MainViewModel @Inject constructor(characterUseCase: CharacterUseCase) : ViewModel() {

    val characters = characterUseCase.getAllCharacters().asLiveData()
}