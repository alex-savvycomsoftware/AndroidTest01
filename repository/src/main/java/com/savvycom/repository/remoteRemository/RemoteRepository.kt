package com.savvycom.repository.remoteRemository

import com.savvycom.data.response.CommentModel
import com.savvycom.data.response.PostModel
import com.savvycom.repository.network.ApiResponse

interface RemoteRepository {

    suspend fun getListPost(): ApiResponse<List<PostModel>>

    suspend fun getPostComments(postId: Int): ApiResponse<List<CommentModel>>

}