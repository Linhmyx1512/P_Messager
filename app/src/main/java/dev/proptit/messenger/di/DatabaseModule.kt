package dev.proptit.messenger.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.proptit.messenger.data.local.AppDatabase
import dev.proptit.messenger.data.local.dao.ContactDao
import dev.proptit.messenger.data.local.dao.MessageDao
import dev.proptit.messenger.data.repository.ContactRepositoryImpl
import dev.proptit.messenger.data.repository.MessageRepositoryImpl
import dev.proptit.messenger.domain.repository.ContactRepository
import dev.proptit.messenger.domain.repository.MessageRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
            .build()
    }

    @Provides
    @Singleton
    fun provideContactDao(database: AppDatabase) = database.contactDao()

    @Provides
    @Singleton
    fun provideMessageDao(database: AppDatabase) = database.messageDao()

    @Provides
    @Singleton
    fun provideContactRepository(contactDao: ContactDao): ContactRepository {
        return ContactRepositoryImpl(contactDao)
    }

    @Provides
    @Singleton
    fun provideMessageRepository(messageDao: MessageDao): MessageRepository {
        return MessageRepositoryImpl(messageDao)
    }
}