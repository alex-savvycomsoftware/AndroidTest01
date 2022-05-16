package com.savvycom.repository.network.service

import com.savvycom.data.response.CommentModel
import com.savvycom.data.response.PostModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/posts")
    suspend fun getListPosts(): Response<List<PostModel>>

    @GET("/posts/{id}/comments")
    suspend fun getPostComments(@Path("id") postId: Int): Response<List<CommentModel>>
}