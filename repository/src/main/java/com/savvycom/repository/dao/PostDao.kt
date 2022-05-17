package com.savvycom.repository.dao

import androidx.room.*
import com.savvycom.data.response.PostModel

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListPost(posts: List<PostModel>)

}