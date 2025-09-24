package see.day.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import see.day.datastore.DataSource
import see.day.datastore.DataStoreDataSource
import see.day.domain.repository.CalendarRepository
import see.day.domain.repository.LoginRepository
import see.day.domain.repository.PhotoRepository
import see.day.domain.repository.UserRepository
import see.day.repository.CalendarRepositoryImpl
import see.day.repository.LoginRepositoryImpl
import see.day.repository.PhotoRepositoryImpl
import see.day.repository.UserRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsDataSource(dataStoreDataSource: DataStoreDataSource): DataSource

    @Binds
    abstract fun bindsLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindsCalendarRepository(calendarRepositoryImpl: CalendarRepositoryImpl): CalendarRepository

    @Binds
    abstract fun bindsPhotoRepository(photoRepositoryImpl: PhotoRepositoryImpl) : PhotoRepository
}
