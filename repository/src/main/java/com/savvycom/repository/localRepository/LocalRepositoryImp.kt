package com.savvycom.repository.localRepository

import com.savvycom.data.response.CommentModel
import com.savvycom.data.response.PostModel
import com.savvycom.repository.component.AppDatabase
import com.savvycom.repository.component.AppPreferences

class LocalRepositoryImp(private val appPreferences: AppPreferences, private val appDatabase: AppDatabase) : LocalRepository {

    override suspend fun insertListPost(posts: List<PostModel>) {
        appDatabase.postDao()?.insertListPost(posts)
    }

    override suspend fun insertListComment(comments: List<CommentModel>) {
        appDatabase.commentDao()?.insertListComment(comments)
    }

}