package org.d3if3102.dicoding.ui.user

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.d3if3102.dicoding.R
import org.d3if3102.dicoding.data.roomDb.DbModule
import org.d3if3102.dicoding.databinding.ActivityUserDetailBinding
import org.d3if3102.dicoding.model.GithubUserDetail
import org.d3if3102.dicoding.model.GithubUserResponse
import org.d3if3102.dicoding.utils.Result

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private val viewModel by viewModels<UserDetailViewModel> {
        UserDetailViewModel.Factory(DbModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val item = intent.getParcelableExtra<GithubUserResponse.Item>("item")
        val username = item?.login ?: ""

        viewModel.resultUserDetail.observe(this) {
            when (it) {
                is Result.Success<*> -> {
                    val user = it.data as GithubUserDetail
                    binding.ivUser.load(user.avatar_url) {
                    }
                    binding.tvFollower.text = user.followers.toString()
                    binding.tvFollowing.text = user.following.toString()
                    binding.tvUser.text = user.name
                    binding.tvUsername.text = user.login
                }
                is Result.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }
        viewModel.getUserDetail(username)


        val fragments = mutableListOf<Fragment>(
            FollowFragment.newInstance(FollowFragment.FOLLOWERS),
            FollowFragment.newInstance(FollowFragment.FOLLOWING)
        )
        val titleFragments = mutableListOf(
            getString(R.string.follower),
            getString(R.string.following)
        )
        val adapter = UserDetailAdapter(this, fragments)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titleFragments[position]
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    viewModel.getFollowers(username)
                } else {
                    viewModel.getFollowing(username)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        viewModel.getFollowers(username)

        viewModel.resultSuccessFavorite.observe(this) {
            binding.fab.changeIconColor(R.color.red)
        }

        viewModel.resultDeleteFavorite.observe(this) {
            binding.fab.changeIconColor(R.color.white)
        }

        binding.fab.setOnClickListener {
            viewModel.setFavorite(item)
        }

        viewModel.findFavorite(item?.id ?: 0) {
            binding.fab.changeIconColor(R.color.red)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
fun FloatingActionButton.changeIconColor(@ColorRes color: Int) {
    imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
}


