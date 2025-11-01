package see.day.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import see.day.datastore.DataSource
import see.day.datastore.DataStoreDataSource
import see.day.domain.repository.CalendarRepository
import see.day.domain.repository.DailyRecordRepository
import see.day.domain.repository.ExerciseRecordRepository
import see.day.domain.repository.FcmRepository
import see.day.domain.repository.HabitRecordRepository
import see.day.domain.repository.LoginRepository
import see.day.domain.repository.NotificationRepository
import see.day.domain.repository.PhotoRepository
import see.day.domain.repository.RecordRepository
import see.day.domain.repository.UserRepository
import see.day.network.fcm.FcmTokenProvider
import see.day.network.fcm.FcmTokenProviderImpl
import see.day.repository.CalendarRepositoryImpl
import see.day.repository.DailyRecordRepositoryImpl
import see.day.repository.ExerciseRecordRepositoryImpl
import see.day.repository.FcmRepositoryImpl
import see.day.repository.HabitRecordRepositoryImpl
import see.day.repository.LoginRepositoryImpl
import see.day.repository.NotificationRepositoryImpl
import see.day.repository.PhotoRepositoryImpl
import see.day.repository.RecordRepositoryImpl
import see.day.repository.UserRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsDataSource(dataStoreDataSource: DataStoreDataSource): DataSource

    @Binds
    abstract fun bindFcmTokenProvider(fcmTokenProviderImpl: FcmTokenProviderImpl): FcmTokenProvider

    @Binds
    abstract fun bindsLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindsCalendarRepository(calendarRepositoryImpl: CalendarRepositoryImpl): CalendarRepository

    @Binds
    abstract fun bindsPhotoRepository(photoRepositoryImpl: PhotoRepositoryImpl): PhotoRepository

    @Binds
    abstract fun bindsDailyRecordRepository(dailyRecordRepositoryImpl: DailyRecordRepositoryImpl): DailyRecordRepository

    @Binds
    abstract fun bindsRecordRepository(recordRepositoryImpl: RecordRepositoryImpl): RecordRepository

    @Binds
    abstract fun bindsExerciseRecordRepository(exerciseRecordRepositoryImpl: ExerciseRecordRepositoryImpl): ExerciseRecordRepository

    @Binds
    abstract fun bindsHabitRecordRepository(habitRecordRepositoryImpl: HabitRecordRepositoryImpl): HabitRecordRepository

    @Binds
    abstract fun bindsNotificationRepository(notificationRepositoryImpl: NotificationRepositoryImpl): NotificationRepository

    @Binds
    abstract fun bindsFcmRepository(fcmRepositoryImpl: FcmRepositoryImpl): FcmRepository
}
