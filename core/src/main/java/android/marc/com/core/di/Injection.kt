package android.marc.com.core.di

import android.content.Context
import android.marc.com.core.data.CharacterRepository
import android.marc.com.core.data.source.local.LocalDataSource
import android.marc.com.core.data.source.local.room.CharacterDatabase
import android.marc.com.core.data.source.remote.RemoteDataSource
import android.marc.com.core.data.source.remote.api.ApiConfig
import android.marc.com.core.domain.usecase.CharacterInteractor
import android.marc.com.core.domain.usecase.CharacterUseCase
import android.marc.com.core.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): CharacterRepository {
        val database = CharacterDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig().getApiService())
        val localDataSource = LocalDataSource.getInstance(database.characterDao())
        val appExecutors = AppExecutors()

        return CharacterRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideCharacterUseCase(context: Context) : CharacterUseCase {
        val repository = provideRepository(context)
        return CharacterInteractor(repository)
    }
}