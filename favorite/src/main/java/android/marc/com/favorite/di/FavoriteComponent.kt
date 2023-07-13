package android.marc.com.favorite.di

import android.marc.com.core.di.CoreComponent
import android.marc.com.favorite.FavoriteActivity
import dagger.Component

@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [FavoriteViewModelModule::class, FavoriteModule::class]
)
interface FavoriteComponent {
    @Component.Factory
    interface Factory {
        fun create(appComponent: CoreComponent) : FavoriteComponent
    }

    fun inject(activity: FavoriteActivity)
}