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
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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

    override fun getAllCharacters(): Flowable<ResourceStatus<List<Character>>> =
        object : NetworkBoundResource<List<CharacterResponse>, List<Character>>(appExecutors) {
            override fun loadFromDB(): Flowable<List<Character>> {
                return localDataSource.getAllCharacters().map {
                    DataMapper.mapEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Character>?): Boolean =
                data == null || data.isEmpty()

            override fun createApiCall(): Flowable<ApiResponse<List<CharacterResponse>>> =
                remoteDataSource.getAllCharacters()

            override fun saveCallResult(data: List<CharacterResponse>) {
                val characterList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertCharacters(characterList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }.asFlowable()

    override fun getFavoriteCharacters(): Flowable<List<Character>> {
        return localDataSource.getFavoriteCharacters().map {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override fun setFavoriteCharacter(characterId: String, state: Boolean) {
        appExecutors.diskIO().execute{ localDataSource.setFavoriteCharacter(characterId, state) }
    }
}