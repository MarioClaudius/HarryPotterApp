package android.marc.com.core.data.source.local.room

import android.marc.com.core.data.source.local.entity.CharacterEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
abstract class CharacterDatabase : RoomDatabase() {

    abstract fun characterDao() : CharacterDao
}