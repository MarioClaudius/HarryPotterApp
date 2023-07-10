package android.marc.com.harrypotterapp.favorite

import android.content.Intent
import android.marc.com.core.data.ResourceStatus
import android.marc.com.core.data.source.local.entity.CharacterEntity
import android.marc.com.core.domain.model.Character
import android.marc.com.core.ui.CharacterAdapter
import android.marc.com.core.utils.DataMapper
import android.marc.com.harrypotterapp.R
import android.marc.com.harrypotterapp.ViewModelFactory
import android.marc.com.harrypotterapp.databinding.ActivityFavoriteBinding
import android.marc.com.harrypotterapp.detail.DetailActivity
import android.marc.com.harrypotterapp.main.MainActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var rvFavoriteCharacter: RecyclerView
    private lateinit var favoriteCharacterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvFavoriteCharacter = binding.rvFavoriteCharacterList
        rvFavoriteCharacter.setHasFixedSize(true)

        supportActionBar?.apply {
            title = "Favorite Character"
        }

        setupRecyclerViewAdapter()
    }

    private fun setupRecyclerViewAdapter() {
        rvFavoriteCharacter.layoutManager = GridLayoutManager(this, 2)
        favoriteCharacterAdapter = CharacterAdapter()
        setupViewModel(favoriteCharacterAdapter)
        rvFavoriteCharacter.adapter = favoriteCharacterAdapter

        favoriteCharacterAdapter.setOnItemClickCallback(object : CharacterAdapter.OnItemClickCallback{
            override fun onItemClicked(character: Character) {
                val intentToDetail = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intentToDetail.putExtra(MainActivity.CHARACTER_KEY_EXTRA, character)
                startActivity(intentToDetail)
            }
        })
    }

    private fun setupViewModel(adapter: CharacterAdapter) {
        val factory = ViewModelFactory.getInstance(this)
        favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        favoriteViewModel.favoriteCharacters.observe(this) { favoriteCharacters ->
            if (favoriteCharacters.isEmpty()) {
                binding.noDataTv.visibility = View.VISIBLE
                binding.rvFavoriteCharacterList.visibility = View.GONE
            } else {
                binding.noDataTv.visibility = View.GONE
                binding.rvFavoriteCharacterList.visibility = View.VISIBLE
                adapter.setData(favoriteCharacters)
            }
        }
    }
}