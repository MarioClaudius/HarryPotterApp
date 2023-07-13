package android.marc.com.harrypotterapp

import android.app.Application
import android.marc.com.core.di.CoreComponent
import android.marc.com.core.di.DaggerCoreComponent
import android.marc.com.harrypotterapp.di.AppComponent
import android.marc.com.harrypotterapp.di.DaggerAppComponent

class MyApplication : Application(){
    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}