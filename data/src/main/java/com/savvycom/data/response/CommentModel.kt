package com.savvycom.data.response

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "Comments", foreignKeys = arrayOf(
        ForeignKey(entity = PostModel::class, parentColumns = arrayOf("id"), childColumns = arrayOf("postId"), onDelete = ForeignKey.CASCADE)
    )
)
data class CommentModel(
    @PrimaryKey
    var id: Int? = null,
    var postId: Int? = null,
    var name: String? = null,
    var email: String? = null,
    var body: String? = null
)