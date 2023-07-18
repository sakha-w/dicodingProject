package org.d3if3102.dicoding.data.roomDb

import androidx.lifecycle.LiveData
import androidx.room.*
import org.d3if3102.dicoding.model.GithubUserResponse

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: GithubUserResponse.Item)

    @Query("SELECT * FROM User")
    fun loadAll(): LiveData<MutableList<GithubUserResponse.Item>>

    @Query("SELECT * FROM User WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): GithubUserResponse.Item

    @Delete
    fun delete(user: GithubUserResponse.Item)
}