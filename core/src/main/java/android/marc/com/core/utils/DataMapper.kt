package android.marc.com.core.utils

import android.marc.com.core.data.source.local.entity.CharacterEntity
import android.marc.com.core.data.source.remote.response.CharacterResponse
import android.marc.com.core.domain.model.Character

object DataMapper {
    fun mapResponsesToEntities(input: List<CharacterResponse>): List<CharacterEntity> {
        val characterList = ArrayList<CharacterEntity>()
        input.map {
            val character = CharacterEntity(
                characterId = it.id,
                name = it.name,
                species = it.species,
                gender = it.gender,
                house = it.house,
                dateOfBirth = it.dateOfBirth ?: "",
                isWizard = it.isWizard,
                ancestry = it.ancestry,
                eyeColor = it.eyeColor,
                hairColor = it.hairColor,
                isHogwartsStudent = it.isHogwartsStudent,
                isHogwartsStaff = it.isHogwartsStaff,
                actor = it.actor,
                isAlive = it.isAlive,
                imageUrl = it.imageUrl,
                isFavorite = false
            )
            characterList.add(character)
        }
        return characterList
    }

    fun mapEntityToDomain(input: CharacterEntity) : Character {
        return Character(
            id = input.characterId,
            name = input.name,
            species = input.species,
            gender = if(input.gender.isNotEmpty()) input.gender else "-",
            house = input.house,
            dateOfBirth = input.dateOfBirth,
            ancestry = input.ancestry,
            actor = input.actor,
            status = if(input.isAlive) "alive" else "dead",
            imageUrl = input.imageUrl,
            isFavorite = input.isFavorite
        )
    }

    fun mapEntityToDomain(inputList: List<CharacterEntity>) : List<Character> = inputList.map { characterEnt ->
        mapEntityToDomain(characterEnt)
    }
}