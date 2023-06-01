package com.achsanit.newsnippet.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.achsanit.newsnippet.data.local.model.BookmarkEntity
import com.achsanit.newsnippet.data.local.model.NewsEntity
import com.achsanit.newsnippet.data.local.model.TopHeadlinesEntity

@Dao
interface NewsnippetDao {
    /**
     * bookmark section
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookmark(data: BookmarkEntity)

    @Query("SELECT * FROM bookmark_table WHERE title=:title")
    fun getBookmarkByTitle(title: String): LiveData<BookmarkEntity>

    @Query("DELETE FROM bookmark_table WHERE title=:title")
    suspend fun deleteBookmarkNews(title: String)

    @Query("SELECT * FROM bookmark_table")
    fun getBookmark(): LiveData<List<BookmarkEntity>>

    @Query("DELETE FROM bookmark_table")
    suspend fun deleteAllBookmarks()
    /**
     * end bookmark section
     */

    /**
     *  offline news by category
     */
    @Query("SELECT * FROM news_category_table")
    fun getLatestNewsByCat(): LiveData<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNewsByCat(data: List<NewsEntity>)

    @Query("DELETE FROM news_category_table")
    suspend fun deleteAllNewsByCat()
    /**
     *  end offline news by category
     */

    /**
     *  offline top headlines
     */
    @Query("SELECT * FROM top_headlines_table")
    fun getLatestTopHeadlines(): LiveData<List<TopHeadlinesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTopHeadline(data: List<TopHeadlinesEntity>)

    @Query("DELETE FROM top_headlines_table")
    suspend fun deleteAllTopHeadline()
    /**
     *  end offline top headlines
     */
}