package org.d3if3102.dicoding.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.d3if3102.dicoding.data.remoteApi.RetrofitApi
import org.d3if3102.dicoding.utils.Result

class UserDetailViewModel : ViewModel() {
    val resultUserDetail = MutableLiveData<Result>()
    val resultUserFollower = MutableLiveData<Result>()
    val resultUserFollowing = MutableLiveData<Result>()

    fun getUserDetail(username: String) {
        viewModelScope.launch {
            flow {
                val response = RetrofitApi
                    .githubService
                    .getDetailUserGithub(username)

                emit(response)
            }.onStart {
                resultUserDetail.value = Result.Loading(true)
            }.onCompletion {
                resultUserDetail.value = Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultUserDetail.value = Result.Error(it)
            }.collect {
                resultUserDetail.value = Result.Success(it)
            }
        }
    }

    fun getFollowers(username: String) {
        viewModelScope.launch {
            flow {
                val response = RetrofitApi
                    .githubService
                    .getFollowersUserGithub(username)

                emit(response)
            }.onStart {
                resultUserFollower.value = Result.Loading(true)
            }.onCompletion {
                resultUserFollower.value = Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultUserFollower.value = Result.Error(it)
            }.collect {
                resultUserFollower.value = Result.Success(it)
            }
        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch {
            flow {
                val response = RetrofitApi
                    .githubService
                    .getFollowingUserGithub(username)

                emit(response)
            }.onStart {
                resultUserFollowing.value = Result.Loading(true)
            }.onCompletion {
                resultUserFollowing.value = Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultUserFollowing.value = Result.Error(it)
            }.collect {
                resultUserFollowing.value = Result.Success(it)
            }
        }
    }


}