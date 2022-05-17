package com.savvycom.repository.remoteRepository

import com.savvycom.data.response.CommentModel
import com.savvycom.data.response.PostModel
import com.savvycom.repository.baseRepository.BaseRemoteRepository
import com.savvycom.repository.network.ApiResponse
import com.savvycom.repository.network.service.ApiService

class RemoteRepositoryImp(
    private val apiService: ApiService,
    private val baseRemoteRepo: BaseRemoteRepository
) : RemoteRepository {

    override suspend fun getListPost(): ApiResponse<List<PostModel>> {
        return baseRemoteRepo.makeApiCall {
            apiService.getListPosts()
        }
    }

    override suspend fun getPostComments(postId: Int): ApiResponse<List<CommentModel>> {
        return baseRemoteRepo.makeApiCall {
            apiService.getPostComments(postId)
        }
    }
}