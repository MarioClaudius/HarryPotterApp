package android.marc.com.core.domain.repository

import android.marc.com.core.data.ResourceStatus
import android.marc.com.core.domain.model.Character
import androidx.lifecycle.LiveData

interface ICharacterRepository {

    fun getAllCharacters() : LiveData<ResourceStatus<List<Character>>>

    fun getFavoriteCharacters(): LiveData<List<Character>>

    fun setFavoriteCharacter(characterId: String, state: Boolean)
}