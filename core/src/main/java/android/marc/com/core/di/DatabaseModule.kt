package android.marc.com.core.di

import android.content.Context
import android.marc.com.core.data.source.local.room.CharacterDao
import android.marc.com.core.data.source.local.room.CharacterDatabase
import androidx.room.Room
import dagger.Module
import dagger.Provides
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    fun provideHelperFactory() : SupportFactory = SupportFactory(
        SQLiteDatabase.getBytes("harrypotter".toCharArray())
    )

    @Singleton
    @Provides
    fun provideDatabase(context: Context, factory: SupportFactory): CharacterDatabase = Room.databaseBuilder(
        context,
        CharacterDatabase::class.java, "Character.db"
    ).fallbackToDestructiveMigration()
        .openHelperFactory(factory)
        .build()

    @Provides
    fun provideCharacterDao(database: CharacterDatabase) : CharacterDao = database.characterDao()
}