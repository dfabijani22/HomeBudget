// feature_home_impl/di/CategoryBindsModule.kt
package hr.foi.air.feature_home_impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hr.foi.air.feature_home_impl.repository.CategoryRepositoryImpl
import hr.foi.feature_home_api.CategoryRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryBindsModule {

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository
}