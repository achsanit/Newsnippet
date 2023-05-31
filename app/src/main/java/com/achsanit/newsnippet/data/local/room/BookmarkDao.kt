package com.achsanit.newsnippet.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.achsanit.newsnippet.data.local.model.NewsEntity

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookmark(data: NewsEntity)

    @Query("SELECT * FROM bookmark_table WHERE title=:title")
    fun getBookmarkByTitle(title: String): LiveData<NewsEntity>

    @Query("DELETE FROM bookmark_table WHERE title=:title")
    suspend fun deleteBookmarkNews(title: String)

    @Query("SELECT * FROM bookmark_table")
    fun getBookmark(): LiveData<List<NewsEntity>>
}