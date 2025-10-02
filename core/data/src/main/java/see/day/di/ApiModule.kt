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
import see.day.datastore.DataStoreDataSource
import see.day.network.AuthService
import see.day.network.CalendarService
import see.day.network.DailyRecordService
import see.day.network.PhotoService
import see.day.network.RecordService
import see.day.network.UserService

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Auth

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Main

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

    @Singleton
    @Provides
    @Auth
    fun provideLoginOkHttp(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @Auth
    fun provideLoginRetrofit(@Auth okHttpClient: OkHttpClient, converterFactory: Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_API_KEY)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @Auth
    fun provideAuthService(@Auth retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    @Main
    fun provideAuthInterceptor(dataStoreDataSource: DataStoreDataSource): AuthInterceptor = AuthInterceptor(dataStoreDataSource)

    @Provides
    @Singleton
    @Main
    fun provideTokenAuthenticator(dataSource: DataStoreDataSource, @Auth authService: AuthService): TokenAuthenticator = TokenAuthenticator(dataSource, authService)

    @Provides
    @Singleton
    @Main
    fun provideMainOkHttpClient(@Main authInterceptor: AuthInterceptor, @Main tokenAuthInterceptor: TokenAuthenticator): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .authenticator(tokenAuthInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Main
    fun provideMainRetrofit(@Main okHttpClient: OkHttpClient, converterFactory: Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_API_KEY)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(@Main retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideCalendarService(@Main retrofit: Retrofit): CalendarService {
        return retrofit.create(CalendarService::class.java)
    }

    @Provides
    @Singleton
    fun providePhotoService(@Main retrofit: Retrofit): PhotoService {
        return retrofit.create(PhotoService::class.java)
    }

    @Provides
    @Singleton
    fun provideDailyRecordService(@Main retrofit: Retrofit): DailyRecordService {
        return retrofit.create(DailyRecordService::class.java)
    }

    @Provides
    @Singleton
    fun provideRecordService(@Main retrofit: Retrofit) : RecordService {
        return retrofit.create(RecordService::class.java)
    }
}
