package android.marc.com.core.data

import android.marc.com.core.data.source.local.LocalDataSource
import android.marc.com.core.data.source.local.entity.CharacterEntity
import android.marc.com.core.data.source.remote.RemoteDataSource
import android.marc.com.core.data.source.remote.api.ApiResponse
import android.marc.com.core.data.source.remote.response.CharacterResponse
import android.marc.com.core.utils.AppExecutors
import android.marc.com.core.utils.DataMapper
import androidx.lifecycle.LiveData

class CharacterRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) {
    companion object {
        @Volatile
        private var instance: CharacterRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): CharacterRepository =
            instance ?: synchronized(this) {
                instance ?: CharacterRepository(remoteData, localData, appExecutors)
            }
    }

    fun getAllCharacters(): LiveData<ResourceStatus<List<CharacterEntity>>> =
        object : NetworkBoundResource<List<CharacterResponse>, List<CharacterEntity>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<CharacterEntity>> {
                return localDataSource.getAllCharacters()
            }

            override fun shouldFetch(data: List<CharacterEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createApiCall(): LiveData<ApiResponse<List<CharacterResponse>>> =
                remoteDataSource.getAllCharacters()

            override fun saveCallResult(data: List<CharacterResponse>) {
                val characterList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertCharacters(characterList)
            }
        }.asLiveData()

    fun getFavoriteCharacters(): LiveData<List<CharacterEntity>> {
        return localDataSource.getFavoriteCharacters()
    }

    fun setFavoriteCharacter(character: CharacterEntity, state: Boolean) {
        appExecutors.diskIO().execute{ localDataSource.setFavoriteCharacter(character, state) }
    }
}