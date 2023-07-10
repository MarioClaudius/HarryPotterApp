package android.marc.com.core.data

import android.marc.com.core.data.source.local.LocalDataSource
import android.marc.com.core.data.source.local.entity.CharacterEntity
import android.marc.com.core.domain.model.Character
import android.marc.com.core.data.source.remote.RemoteDataSource
import android.marc.com.core.data.source.remote.api.ApiResponse
import android.marc.com.core.data.source.remote.response.CharacterResponse
import android.marc.com.core.domain.repository.ICharacterRepository
import android.marc.com.core.utils.AppExecutors
import android.marc.com.core.utils.DataMapper
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

class CharacterRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ICharacterRepository {
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

    override fun getAllCharacters(): LiveData<ResourceStatus<List<Character>>> =
        object : NetworkBoundResource<List<CharacterResponse>, List<Character>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Character>> {
                return Transformations.map(localDataSource.getAllCharacters()) {
                    DataMapper.mapEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Character>?): Boolean =
                data == null || data.isEmpty()

            override fun createApiCall(): LiveData<ApiResponse<List<CharacterResponse>>> =
                remoteDataSource.getAllCharacters()

            override fun saveCallResult(data: List<CharacterResponse>) {
                val characterList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertCharacters(characterList)
            }
        }.asLiveData()

    override fun getFavoriteCharacters(): LiveData<List<Character>> {
        return Transformations.map(localDataSource.getFavoriteCharacters()) {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override fun setFavoriteCharacter(characterId: String, state: Boolean) {
        appExecutors.diskIO().execute{ localDataSource.setFavoriteCharacter(characterId, state) }
    }
}