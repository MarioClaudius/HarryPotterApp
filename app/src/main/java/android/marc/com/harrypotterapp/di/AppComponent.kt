package android.marc.com.harrypotterapp.di

import android.marc.com.core.di.CoreComponent
import android.marc.com.harrypotterapp.detail.DetailActivity
import android.marc.com.harrypotterapp.main.MainActivity
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: DetailActivity)
}