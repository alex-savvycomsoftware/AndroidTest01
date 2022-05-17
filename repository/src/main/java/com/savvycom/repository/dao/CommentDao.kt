package com.savvycom.repository.dao

import androidx.room.*
import com.savvycom.data.response.CommentModel
import com.savvycom.data.response.PostModel

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListComment(posts: List<CommentModel>)

}