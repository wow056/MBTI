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
        return RetrofitBuilder.buildServerRetrofit("http://211.239.124.243:19204/").create(ServerAPI::class.java)
    }
}