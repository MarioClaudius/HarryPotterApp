package android.marc.com.core.ui

import android.marc.com.core.data.source.remote.response.CharacterResponse
import android.marc.com.core.databinding.ItemListDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.marc.com.core.domain.model.Character
import android.util.Log

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private var onItemClickCallBack: OnItemClickCallback? = null

    var characterListData = ArrayList<Character>()
    fun setData(newCharacterListData: List<Character>?) {
        if (newCharacterListData == null) return
        characterListData.clear()
        characterListData.addAll(newCharacterListData)
        notifyDataSetChanged()
    }

    inner class CharacterViewHolder(var binding: ItemListDataBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemListDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characterListData[position]
        Log.d("ADAPTER", character.imageUrl)
        holder.apply {
            Glide.with(itemView.context)
                .load(character.imageUrl)
                .into(binding.characterImg)
            binding.apply {
                tvCharacterName.text = character.name
                itemView.setOnClickListener {
                    onItemClickCallBack?.onItemClicked(character.id)
                }
            }
        }
    }

    override fun getItemCount(): Int = characterListData.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallBack = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(characterId: String)
    }
}