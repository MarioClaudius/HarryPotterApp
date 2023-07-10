package android.marc.com.core.data.source.local

import android.marc.com.core.data.source.local.entity.CharacterEntity
import android.marc.com.core.data.source.local.room.CharacterDao
import androidx.lifecycle.LiveData

class LocalDataSource private constructor(private val characterDao: CharacterDao){
    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(characterDao: CharacterDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(characterDao)
            }
    }

    fun getAllCharacters() : LiveData<List<CharacterEntity>> = characterDao.getAllCharacters()

    fun getFavoriteCharacters() : LiveData<List<CharacterEntity>> = characterDao.getFavoriteCharacters()

    fun insertCharacters(characterList: List<CharacterEntity>) = characterDao.insertCharacters(characterList)

    fun setFavoriteCharacter(characterId: String, newState: Boolean) {
        characterDao.updateFavoriteCharacterById(characterId, newState)
    }
}