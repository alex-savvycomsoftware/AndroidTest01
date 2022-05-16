package com.savvycom.data.response

data class CommentModel(
    var postId: Int? = null,
    var id: Int? = null,
    var name: String? = null,
    var email: String? = null,
    var body: String? = null
)