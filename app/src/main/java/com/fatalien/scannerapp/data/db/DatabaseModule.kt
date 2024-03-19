package com.fatalien.scannerapp.data.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "ScannerApp"
        ).build()
    }

    @Singleton
    @Provides
    fun provideCatalogDao(appDatabase: AppDatabase): CatalogDao {
        return appDatabase.getCatalogDao()
    }

    @Singleton
    @Provides
    fun provideProductDao(appDatabase: AppDatabase): ProductDao {
        return appDatabase.getProductDao()
    }

    @Singleton
    @Provides
    fun provideCatalogRepo(catalogDao: CatalogDao) : CatalogRepository {
        return CatalogRepository(catalogDao)
    }

    @Singleton
    @Provides
    fun provideProductRepo(productDao: ProductDao) : ProductRepository {
        return ProductRepository(productDao)
    }
}