package android.marc.com.harrypotterapp.main

import android.content.Intent
import android.marc.com.core.data.ResourceStatus
import android.marc.com.core.data.source.local.entity.CharacterEntity
import android.marc.com.core.domain.model.Character
import android.marc.com.core.ui.CharacterAdapter
import android.marc.com.core.utils.DataMapper
import android.marc.com.harrypotterapp.R
import android.marc.com.harrypotterapp.ViewModelFactory
import android.marc.com.harrypotterapp.databinding.ActivityMainBinding
import android.marc.com.harrypotterapp.detail.DetailActivity
import android.marc.com.harrypotterapp.favorite.FavoriteActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var rvCharacter: RecyclerView
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvCharacter = binding.rvCharacterList
        rvCharacter.setHasFixedSize(true)

        setupRecyclerViewAdapter()
    }

    private fun setupRecyclerViewAdapter() {
        rvCharacter.layoutManager = GridLayoutManager(this, 2)
        characterAdapter = CharacterAdapter()
        setupViewModel(characterAdapter)
        rvCharacter.adapter = characterAdapter

        characterAdapter.setOnItemClickCallback(object : CharacterAdapter.OnItemClickCallback{
            override fun onItemClicked(character: Character) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra(CHARACTER_KEY_EXTRA, character)
                startActivity(intentToDetail)
            }
        })
    }

    private fun setupViewModel(adapter: CharacterAdapter) {
        val factory = ViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        mainViewModel.characters.observe(this) { characters ->
            if (characters != null) {
                when(characters) {
                    is ResourceStatus.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is ResourceStatus.Success -> {
                        binding.progressBar.visibility = View.GONE
                        adapter.setData(characters.data)
                    }
                    is ResourceStatus.Error -> {
                        binding.progressBar.visibility = View.GONE
                        // tambahan ui error jika perlu
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.favorite -> {
                val intentToFavorite = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intentToFavorite)
                true
            }
            else -> true
        }
    }

    companion object {
        const val CHARACTER_KEY_EXTRA = "character_key_extra"
    }
}