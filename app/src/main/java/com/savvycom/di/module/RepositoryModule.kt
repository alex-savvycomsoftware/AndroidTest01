package com.savvycom.di.module

import com.savvycom.repository.Repository
import com.savvycom.repository.RepositoryImp
import com.savvycom.repository.baseRepository.BaseRemoteRepository
import com.savvycom.repository.baseRepository.BaseRemoteRepositoryImp
import com.savvycom.repository.localRepository.LocalRepository
import com.savvycom.repository.localRepository.LocalRepositoryImp
import com.savvycom.repository.remoteRemository.RemoteRepository
import com.savvycom.repository.remoteRemository.RemoteRepositoryImp
import org.koin.dsl.module

val repoModule = module {

    single<BaseRemoteRepository> {
        return@single BaseRemoteRepositoryImp()
    }

    single<RemoteRepository> {
        return@single RemoteRepositoryImp(get(), get())
    }

    single<LocalRepository> {
        return@single LocalRepositoryImp(get())
    }

    single<Repository> {
        return@single RepositoryImp(get(), get())
    }


}