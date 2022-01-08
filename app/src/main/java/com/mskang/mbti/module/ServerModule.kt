package com.mskang.mbti.module

import com.mskang.mbti.api.ServerAPI
import com.mskang.mbti.api.retrofit.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ServerModule {
    @Provides
    fun provideServerAPI(): ServerAPI {
        return RetrofitBuilder.buildServerRetrofit("http://192.168.1.11:8080/").create(ServerAPI::class.java)
    }
}