package com.amanpathak.bitbucketaccess.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.amanpathak.bitbucketaccess.databinding.FragmentHomeBinding
import com.amanpathak.bitbucketaccess.model.RepoModel
import com.amanpathak.bitbucketaccess.network.model.RepoListItem
import com.amanpathak.bitbucketaccess.utils.AppNavigator

class HomeFragment : Fragment(), RepoAdapter.RepoAdapterInteraction {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel : HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        listeners()
        return binding.root
    }

    private fun listeners() {

        viewModel.repoList.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                val adapter = RepoAdapter(this, it)
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                binding.recyclerView.adapter = adapter
            }
        })


    }

    override fun onRepoClick(repoItem: RepoModel) {
        val bundle = Bundle()
        bundle.putParcelable(RepoDetailScreen.REPO_DETAIL_MODEL,repoItem)
        AppNavigator.navigateToRepoDetail(requireActivity(), bundle)
    }


}