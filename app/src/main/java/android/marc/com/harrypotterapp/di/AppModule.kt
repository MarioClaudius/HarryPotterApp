package android.marc.com.harrypotterapp.di

import android.marc.com.core.domain.usecase.CharacterInteractor
import android.marc.com.core.domain.usecase.CharacterUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideCharacterUseCase(characterInteractor: CharacterInteractor): CharacterUseCase
}