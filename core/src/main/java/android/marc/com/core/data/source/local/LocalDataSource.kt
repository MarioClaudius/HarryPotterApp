package android.marc.com.core.data.source.local

import android.marc.com.core.data.source.local.entity.CharacterEntity
import android.marc.com.core.data.source.local.room.CharacterDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource private constructor(private val characterDao: CharacterDao){
    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(characterDao: CharacterDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(characterDao)
            }
    }

    fun getAllCharacters() : Flow<List<CharacterEntity>> = characterDao.getAllCharacters()

    fun getFavoriteCharacters() : Flow<List<CharacterEntity>> = characterDao.getFavoriteCharacters()

    suspend fun insertCharacters(characterList: List<CharacterEntity>) = characterDao.insertCharacters(characterList)

    fun setFavoriteCharacter(characterId: String, newState: Boolean) {
        characterDao.updateFavoriteCharacterById(characterId, newState)
    }
}