package com.savvycom.repository

import com.savvycom.repository.localRepository.LocalRepository
import com.savvycom.repository.remoteRemository.RemoteRepository

interface Repository : LocalRepository, RemoteRepository {

}