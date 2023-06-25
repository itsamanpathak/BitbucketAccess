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
import com.google.android.material.snackbar.Snackbar

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

        viewModel.fetchRepoList()
        viewModel.repoList.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                val adapter = RepoAdapter(this, it)
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                binding.recyclerView.adapter = adapter
            }
        })


        viewModel.event.observe(viewLifecycleOwner, Observer {
            when(it){

                is HomeViewModel.HomeEvent.ShowSnackBar -> {
                    if(!it.messageString.isNullOrEmpty()) {
                        Snackbar.make(requireActivity().findViewById(android.R.id.content),it.messageString, Snackbar.LENGTH_LONG).show()
                    }
                }
                is HomeViewModel.HomeEvent.InternetNotConnected -> {
                    if(it.showRetry){
                        val snackBar = Snackbar.make(requireActivity().findViewById(android.R.id.content),it.messageString.toString(), Snackbar.LENGTH_LONG)
                        snackBar.setAction("Retry", View.OnClickListener {
                            viewModel.onRetryClick()
                        })
                    }
                }

                else -> {}
            }

        })

    }





    override fun onRepoClick(repoItem: RepoModel) {
        val bundle = Bundle()
        bundle.putParcelable(RepoDetailScreen.REPO_DETAIL_MODEL,repoItem)
        AppNavigator.navigateToRepoDetail(requireActivity(), bundle)
    }


}