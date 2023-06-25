package com.amanpathak.bitbucketaccess.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.amanpathak.bitbucketaccess.R
import com.amanpathak.bitbucketaccess.databinding.RepoListItemBinding
import com.amanpathak.bitbucketaccess.model.RepoModel
import com.bumptech.glide.Glide


class RepoAdapter(val context: Fragment, private val list: List<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(val item: RepoListItemBinding) : RecyclerView.ViewHolder(item!!.root) {

        fun bind(data : Any){
             val repo = data as RepoModel
             item.name.text = repo.name
             item.desc.text = repo.des
             Glide.with(context).load(R.drawable.repo_icon)
                 .into(item.icon)

            itemView.setOnClickListener {
                (context as RepoAdapterInteraction).onRepoClick(repo)
            }

        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            RepoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list[position]
        if(holder is ViewHolder){
            holder.bind(data)

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface RepoAdapterInteraction{
        fun onRepoClick(repoItem : RepoModel)
    }



}