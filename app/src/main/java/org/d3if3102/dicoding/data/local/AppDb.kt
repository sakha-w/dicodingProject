package org.d3if3102.dicoding.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.d3if3102.dicoding.model.GithubUserResponse

@Database(entities = [GithubUserResponse.Item::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun userDao(): UserDao
}