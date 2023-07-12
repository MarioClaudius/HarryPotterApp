package android.marc.com.core.di

import android.content.Context
import android.marc.com.core.data.source.local.room.CharacterDao
import android.marc.com.core.data.source.local.room.CharacterDatabase
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): CharacterDatabase = Room.databaseBuilder(
        context,
        CharacterDatabase::class.java, "Character.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideCharacterDao(database: CharacterDatabase) : CharacterDao = database.characterDao()
}