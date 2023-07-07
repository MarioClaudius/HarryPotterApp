package android.marc.com.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("species")
    val species: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("house")
    val house: String,

    @field:SerializedName("dateOfBirth")
    val dateOfBirth: String,

    @field:SerializedName("wizard")
    val isWizard: Boolean,

    @field:SerializedName("ancestry")
    val ancestry: String,

    @field:SerializedName("eyeColour")
    val eyeColor: String,

    @field:SerializedName("hairColour")
    val hairColor: String,

    @field:SerializedName("hogwartsStudent")
    val isHogwartsStudent: Boolean,

    @field:SerializedName("hogwartsStaff")
    val isHogwartsStaff: Boolean,

    @field:SerializedName("actor")
    val actor: String,

    @field:SerializedName("alive")
    val isAlive: Boolean,

    @field:SerializedName("image")
    val imageUrl: String
)