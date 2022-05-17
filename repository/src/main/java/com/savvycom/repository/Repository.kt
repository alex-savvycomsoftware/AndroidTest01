package com.savvycom.repository

import com.savvycom.repository.localRepository.LocalRepository
import com.savvycom.repository.remoteRepository.RemoteRepository

interface Repository : LocalRepository, RemoteRepository {

}