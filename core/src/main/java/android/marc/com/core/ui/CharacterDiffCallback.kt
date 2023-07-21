package android.marc.com.core.ui

import android.marc.com.core.domain.model.Character
import androidx.recyclerview.widget.DiffUtil

class CharacterDiffCallback(private val mOldCharacterList : List<Character>, private val mNewCharacterList: List<Character>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = mOldCharacterList.size

    override fun getNewListSize(): Int = mNewCharacterList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldCharacterList[oldItemPosition].id == mNewCharacterList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = mOldCharacterList[oldItemPosition]
        val newItem = mNewCharacterList[newItemPosition]
        return (oldItem.name == newItem.name && oldItem.isFavorite == newItem.isFavorite &&
                oldItem.imageUrl == newItem.imageUrl && oldItem.actor == newItem.actor &&
                oldItem.ancestry == newItem.ancestry && oldItem.dateOfBirth == newItem.dateOfBirth &&
                oldItem.gender == newItem.gender && oldItem.house == newItem.house &&
                oldItem.species == newItem.species && oldItem.status == newItem.status)
    }
}