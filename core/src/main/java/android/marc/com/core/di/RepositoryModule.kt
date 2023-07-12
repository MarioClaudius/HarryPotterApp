package android.marc.com.core.di

import android.marc.com.core.data.CharacterRepository
import android.marc.com.core.domain.repository.ICharacterRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(characterRepository: CharacterRepository): ICharacterRepository
}