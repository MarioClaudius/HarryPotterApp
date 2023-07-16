package android.marc.com.favorite

import android.content.Intent
import android.marc.com.core.domain.model.Character
import android.marc.com.core.ui.CharacterAdapter
import android.marc.com.favorite.databinding.ActivityFavoriteBinding
import android.marc.com.favorite.di.DaggerFavoriteComponent
import android.marc.com.favorite.ui.FavoriteViewModelFactory
import android.marc.com.harrypotterapp.MyApplication
import android.marc.com.harrypotterapp.detail.DetailActivity
import android.marc.com.harrypotterapp.main.MainActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var rvFavoriteCharacter: RecyclerView
    private lateinit var favoriteCharacterAdapter: CharacterAdapter

    @Inject
    lateinit var factory : FavoriteViewModelFactory
    private val favoriteViewModel : FavoriteViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val coreComponent = (application as MyApplication).coreComponent
        DaggerFavoriteComponent.factory().create(coreComponent).inject(this)
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