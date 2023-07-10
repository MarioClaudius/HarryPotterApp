package android.marc.com.core.data.source.local.room

import android.marc.com.core.data.source.local.entity.CharacterEntity
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character")
    fun getAllCharacters() : LiveData<List<CharacterEntity>>

    @Query("SELECT * FROM character WHERE is_favorite = 1")
    fun getFavoriteCharacters() : LiveData<List<CharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(characters: List<CharacterEntity>)

    @Query("UPDATE character SET is_favorite = :newState WHERE character_id = :characterId")
    fun updateFavoriteCharacterById(characterId: String, newState: Boolean)

    @Update
    fun updateFavoriteCharacter(character: CharacterEntity)
}