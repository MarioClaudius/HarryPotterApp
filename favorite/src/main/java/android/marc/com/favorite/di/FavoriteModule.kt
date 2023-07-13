package android.marc.com.favorite.di

import android.marc.com.core.domain.usecase.CharacterInteractor
import android.marc.com.core.domain.usecase.CharacterUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class FavoriteModule {
    @Binds
    abstract fun provideCharacterUseCase(characterInteractor: CharacterInteractor): CharacterUseCase
}