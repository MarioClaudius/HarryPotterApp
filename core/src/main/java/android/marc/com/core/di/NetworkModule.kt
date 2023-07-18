package android.marc.com.core.di

import android.marc.com.core.data.source.remote.api.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    fun provideCertificatePinner(): CertificatePinner = CertificatePinner.Builder()
        .add("hp-api.onrender.com", "sha256/FhZ/ntpC0jSQ+ZBqPeQVbLCf29Pb+k9Ki31luISkWQo=")
        .add("hp-api.onrender.com", "sha256/8xoDcrs2LNLr7MXkvzFEmk5ILiKRIILkK1sEKWZy5Oo=")
        .build()

    @Provides
    fun provideOkHttpClient(certificatePinner: CertificatePinner): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}