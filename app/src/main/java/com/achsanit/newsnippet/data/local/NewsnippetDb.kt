package com.achsanit.newsnippet.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.achsanit.newsnippet.data.local.model.BookmarkEntity
import com.achsanit.newsnippet.data.local.model.NewsEntity
import com.achsanit.newsnippet.data.local.model.TopHeadlinesEntity
import com.achsanit.newsnippet.data.local.room.NewsnippetDao

@Database(
    entities = [
        NewsEntity::class,
        TopHeadlinesEntity::class,
        BookmarkEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class NewsnippetDb: RoomDatabase() {
    abstract fun newsnippetDao(): NewsnippetDao
}