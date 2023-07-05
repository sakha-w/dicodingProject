package org.d3if3102.dicoding.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import org.d3if3102.dicoding.databinding.UserListBinding
import org.d3if3102.dicoding.model.GithubUserResponse

class UserAdapter(
    private val data: MutableList<GithubUserResponse.Item> = mutableListOf(),
    private val listener: (GithubUserResponse.Item) -> Unit
) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    fun setData(data: MutableList<GithubUserResponse.Item>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val v: UserListBinding) : RecyclerView.ViewHolder(v.root) {
        fun bind(item: GithubUserResponse.Item) {
            v.ivUser.load(item.avatar_url) {
                transformations(CircleCropTransformation())
            }

            v.tvUser.text = item.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(UserListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

    override fun getItemCount(): Int = data.size
}


