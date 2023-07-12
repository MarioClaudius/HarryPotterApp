package android.marc.com.core.data.source.local

import android.marc.com.core.data.source.local.entity.CharacterEntity
import android.marc.com.core.data.source.local.room.CharacterDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val characterDao: CharacterDao){

    fun getAllCharacters() : Flow<List<CharacterEntity>> = characterDao.getAllCharacters()

    fun getFavoriteCharacters() : Flow<List<CharacterEntity>> = characterDao.getFavoriteCharacters()

    suspend fun insertCharacters(characterList: List<CharacterEntity>) = characterDao.insertCharacters(characterList)

    fun setFavoriteCharacter(characterId: String, newState: Boolean) {
        characterDao.updateFavoriteCharacterById(characterId, newState)
    }
}