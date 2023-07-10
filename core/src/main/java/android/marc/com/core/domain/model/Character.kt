package android.marc.com.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    val id: String,
    val name: String,
    val species: String,
    val gender: String,
    val house: String,
    val dateOfBirth: String,
    val ancestry: String,
    val actor: String,
    val status: String,
    val imageUrl: String,
    val isFavorite: Boolean
) : Parcelable
