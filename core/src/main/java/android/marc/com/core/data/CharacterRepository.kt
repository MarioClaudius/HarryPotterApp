package android.marc.com.core.data

import android.marc.com.core.data.source.local.LocalDataSource
import android.marc.com.core.data.source.remote.RemoteDataSource
import android.marc.com.core.data.source.remote.api.ApiResponse
import android.marc.com.core.data.source.remote.response.CharacterResponse
import android.marc.com.core.domain.model.Character
import android.marc.com.core.domain.repository.ICharacterRepository
import android.marc.com.core.utils.AppExecutors
import android.marc.com.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ICharacterRepository {
    override fun getAllCharacters(): Flow<ResourceStatus<List<Character>>> =
        object : NetworkBoundResource<List<CharacterResponse>, List<Character>>(appExecutors) {
            override fun loadFromDB(): Flow<List<Character>> {
                return localDataSource.getAllCharacters().map {
                    DataMapper.mapEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Character>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createApiCall(): Flow<ApiResponse<List<CharacterResponse>>> =
                remoteDataSource.getAllCharacters()

            override suspend fun saveCallResult(data: List<CharacterResponse>) {
                val characterList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertCharacters(characterList)
            }
        }.asFlow()

    override fun getFavoriteCharacters(): Flow<List<Character>> {
        return localDataSource.getFavoriteCharacters().map {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override fun setFavoriteCharacter(characterId: String, state: Boolean) {
        appExecutors.diskIO().execute{ localDataSource.setFavoriteCharacter(characterId, state) }
    }
}