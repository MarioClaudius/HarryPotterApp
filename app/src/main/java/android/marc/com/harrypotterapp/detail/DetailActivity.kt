package android.marc.com.harrypotterapp.detail

import android.marc.com.harrypotterapp.databinding.ActivityDetailBinding
import android.marc.com.harrypotterapp.main.MainActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private lateinit var characterId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        characterId = intent.getStringExtra(MainActivity.CHARACTER_ID_KEY_EXTRA) ?: ""

        binding.test.text = characterId
    }
}