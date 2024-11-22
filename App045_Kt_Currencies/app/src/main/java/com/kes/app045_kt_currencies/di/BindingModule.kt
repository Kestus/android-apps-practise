package com.kes.app045_kt_currencies.di

import com.kes.app045_kt_currencies.data.repository.RepositoryImpl
import com.kes.app045_kt_currencies.domain.Repository
import dagger.Binds
import dagger.Module

@Module
interface BindingModule {

    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

}