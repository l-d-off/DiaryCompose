package ru.darf.diarycompose.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.darf.diarycompose.data.database.AppDatabase
import ru.darf.diarycompose.data.database.EventDao
import ru.darf.diarycompose.data.network.ApiService
import ru.darf.diarycompose.data.repository.EventRepositoryImpl
import ru.darf.diarycompose.domain.EventRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindEventRepository(impl: EventRepositoryImpl): EventRepository

    companion object {
        @Provides
        fun provideEventDao(application: Application): EventDao {
            return AppDatabase.getInstance(application).eventDao()
        }

        @Provides
        fun provideApiService(): ApiService {
            return ApiService
        }
    }
}
