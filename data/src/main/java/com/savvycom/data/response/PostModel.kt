package com.savvycom.data.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Posts")
data class PostModel(
    @PrimaryKey
    var id: Int? = null,
    var userId: Int? = null,
    var title: String? = null,
    var body: String? = null
) : Parcelable