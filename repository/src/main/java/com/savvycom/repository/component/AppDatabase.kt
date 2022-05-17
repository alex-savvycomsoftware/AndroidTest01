package com.savvycom.repository.component

import androidx.room.Database
import androidx.room.RoomDatabase
import com.savvycom.data.response.CommentModel
import com.savvycom.data.response.PostModel
import com.savvycom.repository.dao.CommentDao
import com.savvycom.repository.dao.PostDao


@Database(entities = [PostModel::class, CommentModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao?
    abstract fun commentDao(): CommentDao?
}