package android.marc.com.harrypotterapp

import android.content.Context
import android.marc.com.core.data.CharacterRepository
import android.marc.com.core.di.Injection
import android.marc.com.core.domain.usecase.CharacterUseCase
import android.marc.com.harrypotterapp.detail.DetailViewModel
import android.marc.com.harrypotterapp.favorite.FavoriteViewModel
import android.marc.com.harrypotterapp.main.MainViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory private constructor(private val characterUseCase: CharacterUseCase): ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance
                ?: synchronized(this) {
                    instance ?: ViewModelFactory(Injection.provideCharacterUseCase(context))
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(characterUseCase) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(characterUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(characterUseCase) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}