package com.achsanit.newsnippet.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.achsanit.newsnippet.data.local.model.NewsEntity
import com.achsanit.newsnippet.data.local.room.BookmarkDao

@Database(
    entities = [NewsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsnippetDb: RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}