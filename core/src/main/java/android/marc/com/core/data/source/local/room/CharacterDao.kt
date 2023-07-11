package android.marc.com.core.data.source.local.room

import android.marc.com.core.data.source.local.entity.CharacterEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character")
    fun getAllCharacters() : Flow<List<CharacterEntity>>

    @Query("SELECT * FROM character WHERE is_favorite = 1")
    fun getFavoriteCharacters() : Flow<List<CharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>) //: Completable

    @Query("UPDATE character SET is_favorite = :newState WHERE character_id = :characterId")
    fun updateFavoriteCharacterById(characterId: String, newState: Boolean)
}