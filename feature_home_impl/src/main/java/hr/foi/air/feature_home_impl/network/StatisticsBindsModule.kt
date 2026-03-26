package hr.foi.air.feature_home_impl.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hr.foi.air.feature_home_impl.repository.StatisticsRepositoryImpl
import hr.foi.feature_home_api.StatisticsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StatisticsBindsModule {

    @Binds
    @Singleton
    abstract fun bindStatisticsRepository(
        impl: StatisticsRepositoryImpl
    ): StatisticsRepository
}