package org.d3if3102.dicoding.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3102.dicoding.data.roomDb.DbModule

class FavoriteViewModel(private val dbModule: DbModule) : ViewModel() {

    fun getUserFavorite() = dbModule.userDao.loadAll()

    class Factory(private val db: DbModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavoriteViewModel(db) as T
    }
}