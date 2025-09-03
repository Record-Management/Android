package see.day.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import see.day.datastore.DataSource
import see.day.datastore.DataStoreDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsDataSource(dataStoreDataSource: DataStoreDataSource): DataSource
}
