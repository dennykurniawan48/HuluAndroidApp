package com.signaltekno.huluapp.dependency

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import kotlinx.serialization.json.Json as KotlinJson

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideClient(): HttpClient {
        return HttpClient(Android){
            install(JsonFeature){
                serializer = KotlinxSerializer(KotlinJson {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
                acceptContentTypes += ContentType("application", "json+hal")
            }
        }
    }
}