package dev.proptit.messenger.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.proptit.messenger.data.maper.ContactMapper
import dev.proptit.messenger.data.maper.MessageMapper
import dev.proptit.messenger.data.remote.NetworkManager
import dev.proptit.messenger.data.remote.service.ContactService
import dev.proptit.messenger.data.remote.service.MessageService
import dev.proptit.messenger.domain.repository.ContactRepository
import dev.proptit.messenger.domain.repository.MessageRepository
import dev.proptit.messenger.setup.PrefManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://34.124.219.119:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideContactService(retrofit: Retrofit): ContactService {
        return retrofit.create(ContactService::class.java)
    }

    @Provides
    @Singleton
    fun provideMessageService(retrofit: Retrofit): MessageService {
        return retrofit.create(MessageService::class.java)
    }

    @Provides
    @Singleton
    fun provideContactMapper(): ContactMapper {
        return ContactMapper
    }

    @Provides
    @Singleton
    fun provideMessageMapper(): MessageMapper {
        return MessageMapper
    }

    @Provides
    @Singleton
    fun provideNetworkManager(
        contactService: ContactService,
        contactRepository: ContactRepository,
        messageService: MessageService,
        messageRepository: MessageRepository,
        contactMapper : ContactMapper,
        messageMapper : MessageMapper,
        prefManager: PrefManager
    ): NetworkManager {
        return NetworkManager(
            contactService,
            contactRepository,
            messageService,
            messageRepository,
            contactMapper,
            messageMapper,
            prefManager
        )
    }
}