package android.marc.com.harrypotterapp.main

import android.content.Intent
import android.marc.com.core.ui.CharacterAdapter
import android.marc.com.harrypotterapp.R
import android.marc.com.harrypotterapp.ViewModelFactory
import android.marc.com.harrypotterapp.databinding.ActivityMainBinding
import android.marc.com.harrypotterapp.detail.DetailActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            override fun onItemClicked(characterId: String) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra(CHARACTER_ID_KEY_EXTRA, characterId)
                startActivity(intentToDetail)
            }
        })
    }

    private fun setupViewModel(adapter: CharacterAdapter) {
        mainViewModel = ViewModelProvider(this, ViewModelFactory())[MainViewModel::class.java]

        mainViewModel.characterList.observe(this) { characterList ->
            adapter.setData(characterList)
        }

        mainViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    companion object {
        const val CHARACTER_ID_KEY_EXTRA = "character_id_key_extra"
    }
}