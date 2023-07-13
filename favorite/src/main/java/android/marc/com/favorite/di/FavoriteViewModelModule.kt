package android.marc.com.favorite.di

import android.marc.com.favorite.FavoriteViewModel
import android.marc.com.favorite.ui.FavoriteViewModelFactory
import android.marc.com.harrypotterapp.di.ViewModelKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class FavoriteViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindFavoriteViewModel(viewModel: FavoriteViewModel) : ViewModel

    @Binds
    abstract fun bindFavoriteViewModelFactory(factory: FavoriteViewModelFactory): ViewModelProvider.Factory
}