package com.savvycom.repository

import com.savvycom.data.response.CommentModel
import com.savvycom.data.response.PostModel
import com.savvycom.repository.localRepository.LocalRepository
import com.savvycom.repository.network.ApiResponse
import com.savvycom.repository.remoteRepository.RemoteRepository

class RepositoryImp(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) :
    Repository {

    override suspend fun insertListPost(posts: List<PostModel>) {
        return localRepository.insertListPost(posts)
    }

    override suspend fun insertListComment(comments: List<CommentModel>) {
        return localRepository.insertListComment(comments)
    }

    override suspend fun getListPost(): ApiResponse<List<PostModel>> {
        return remoteRepository.getListPost()
    }

    override suspend fun getPostComments(postId: Int): ApiResponse<List<CommentModel>> {
        return remoteRepository.getPostComments(postId)
    }

}