package see.day.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import see.day.data.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Login

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideConverterFactory(json: Json): Factory {
        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    @Login
    fun provideLoginOkHttp(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @Login
    fun provideLoginRetrofit(
        @Login okHttpClient: OkHttpClient,
        converterFactory: Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_API_KEY)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }
}
