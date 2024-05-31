package android.marc.com.harrypotterapp.main

import android.content.Intent
import android.marc.com.core.data.ResourceStatus
import android.marc.com.core.domain.model.Character
import android.marc.com.core.ui.CharacterAdapter
import android.marc.com.harrypotterapp.MyApplication
import android.marc.com.harrypotterapp.R
import android.marc.com.harrypotterapp.databinding.ActivityMainBinding
import android.marc.com.harrypotterapp.detail.DetailActivity
import android.marc.com.harrypotterapp.ui.ViewModelFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var factory: ViewModelFactory
    private val mainViewModel: MainViewModel by viewModels {
        factory
    }
    private lateinit var rvCharacter: RecyclerView
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
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
                try {
                    installFavoriteModule()
                    moveToFavoriteActivity()
                } catch (e: Exception) {
                    Toast.makeText(this, "Module not found", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> true
        }
    }

    private fun installFavoriteModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val moduleFavorite = "favorite"
        if (splitInstallManager.installedModules.contains(moduleFavorite)) {
            moveToFavoriteActivity()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleFavorite)
                .build()

            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(this, "Module installed successfully", Toast.LENGTH_SHORT).show()
                    moveToFavoriteActivity()
                }
                .addOnFailureListener {
                    Log.e("install module", it.message.toString())
                    Toast.makeText(this, "Error, failed to install module", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun moveToFavoriteActivity() {
        val uri = Uri.parse("harrypotterapp://favorite")
        val intentToFavorite = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intentToFavorite)
    }

    companion object {
        const val CHARACTER_KEY_EXTRA = "character_key_extra"
    }
}