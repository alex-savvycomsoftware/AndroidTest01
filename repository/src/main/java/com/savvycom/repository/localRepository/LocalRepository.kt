package com.savvycom.repository.localRepository

import com.savvycom.data.response.CommentModel
import com.savvycom.data.response.PostModel

interface LocalRepository {

    suspend fun insertListPost(posts: List<PostModel>)
    suspend fun insertListComment(comments: List<CommentModel>)

}