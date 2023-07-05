package org.d3if3102.dicoding.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.d3if3102.dicoding.databinding.FragmentFollowBinding
import org.d3if3102.dicoding.model.GithubUserResponse
import org.d3if3102.dicoding.ui.main.UserAdapter
import org.d3if3102.dicoding.utils.Result


class FollowFragment : Fragment() {

    private var binding: FragmentFollowBinding? = null
    private val adapter by lazy {
        UserAdapter {

        }
    }
    private val viewModel by activityViewModels<UserDetailViewModel>()
    var type = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvFollow?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowFragment.adapter
    }
            when (type) {
                FOLLOWERS -> {
                    viewModel.resultUserFollower.observe(viewLifecycleOwner, this::manageResultFollows)
                }

                FOLLOWING -> {
                    viewModel.resultUserFollowing.observe(viewLifecycleOwner, this::manageResultFollows)
                }
            }
        }

    private fun manageResultFollows(state: Result) {
        when (state) {
            is Result.Success<*> -> {
                adapter.setData(state.data as MutableList<GithubUserResponse.Item>)
            }
            is Result.Error -> {
                Toast.makeText(
                    requireActivity(),
                    state.exception.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Result.Loading -> {
                binding?.progressBar?.isVisible = state.isLoading
            }
        }
    }

    companion object {
        const val FOLLOWERS = 100
        const val FOLLOWING = 101

        fun newInstance(type: Int) = FollowFragment()
            .apply {
                this.type = type
            }

    }
}