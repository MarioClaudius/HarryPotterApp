package android.marc.com.harrypotterapp.detail

import android.marc.com.core.domain.model.Character
import android.marc.com.harrypotterapp.R
import android.marc.com.harrypotterapp.ViewModelFactory
import android.marc.com.harrypotterapp.databinding.ActivityDetailBinding
import android.marc.com.harrypotterapp.main.MainActivity
import android.marc.com.harrypotterapp.main.MainViewModel
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import android.marc.com.core.R.drawable
import androidx.core.content.ContextCompat

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        detailViewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]


        val detailCharacter = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(MainActivity.CHARACTER_KEY_EXTRA, Character::class.java)
        } else {
            intent.getParcelableExtra(MainActivity.CHARACTER_KEY_EXTRA)
        }
        showCharacterDetail(detailCharacter)
    }

    private fun showCharacterDetail(character: Character?) {
        character?.let {
            binding.apply {
                tvName.text = character.name
                tvSpecies.text = character.species
                tvGender.text = character.gender
                tvHouse.text = character.house
                tvBirthDate.text = character.dateOfBirth
                tvAncestry.text = character.ancestry
                tvActor.text = character.actor
                tvStatus.text = character.status

                Glide.with(applicationContext)
                    .load(if(character.imageUrl.isEmpty()) drawable.unknown_profile else character.imageUrl)
                    .into(imgCharacter)

                var favoriteStatus = character.isFavorite
                setIconFavoriteStatus(favoriteStatus)
                fab.setOnClickListener {
                    favoriteStatus = !favoriteStatus
                    detailViewModel.setFavoriteCharacter(character.id, favoriteStatus)
                    setIconFavoriteStatus(favoriteStatus)
                }
            }
        }
    }

    private fun setIconFavoriteStatus(favoriteStatus: Boolean) {
        if (favoriteStatus) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border))
        }
    }
}