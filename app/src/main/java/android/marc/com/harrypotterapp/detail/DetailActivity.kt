package android.marc.com.harrypotterapp.detail

import android.marc.com.core.R.drawable
import android.marc.com.core.domain.model.Character
import android.marc.com.harrypotterapp.MyApplication
import android.marc.com.harrypotterapp.R
import android.marc.com.harrypotterapp.ui.ViewModelFactory
import android.marc.com.harrypotterapp.databinding.ActivityDetailBinding
import android.marc.com.harrypotterapp.main.MainActivity
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    @Inject
    lateinit var factory: ViewModelFactory
    private val detailViewModel: DetailViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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