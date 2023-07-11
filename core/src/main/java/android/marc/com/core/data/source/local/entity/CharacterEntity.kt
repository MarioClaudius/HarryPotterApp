package android.marc.com.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "character_id")
    var characterId: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "species")
    var species: String,

    @ColumnInfo(name = "gender")
    var gender: String,

    @ColumnInfo(name = "house")
    var house: String,

    @ColumnInfo(name = "date_of_birth")
    @Nullable
    var dateOfBirth: String,

    @ColumnInfo(name = "is_wizard")
    var isWizard: Boolean,

    @ColumnInfo(name = "ancestry")
    var ancestry: String,

    @ColumnInfo(name = "eye_color")
    var eyeColor: String,

    @ColumnInfo(name = "hair_color")
    var hairColor: String,

    @ColumnInfo(name = "is_hogwarts_student")
    var isHogwartsStudent: Boolean,

    @ColumnInfo(name = "is_hogwarts_staff")
    var isHogwartsStaff: Boolean,

    @ColumnInfo(name = "actor")
    var actor: String,

    @ColumnInfo(name = "is_alive")
    var isAlive: Boolean,

    @ColumnInfo(name = "image_url")
    var imageUrl: String,

    @ColumnInfo(name = "is_favorite")
    var isFavorite : Boolean = false
)
