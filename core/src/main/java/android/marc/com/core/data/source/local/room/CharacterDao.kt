package android.marc.com.core.data.source.local.room

import android.marc.com.core.data.source.local.entity.CharacterEntity
import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character")
    fun getAllCharacters() : Flowable<List<CharacterEntity>>

    @Query("SELECT * FROM character WHERE is_favorite = 1")
    fun getFavoriteCharacters() : Flowable<List<CharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(characters: List<CharacterEntity>) : Completable

    @Query("UPDATE character SET is_favorite = :newState WHERE character_id = :characterId")
    fun updateFavoriteCharacterById(characterId: String, newState: Boolean)
}