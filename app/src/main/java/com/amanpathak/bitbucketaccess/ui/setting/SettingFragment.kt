package com.amanpathak.bitbucketaccess.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amanpathak.bitbucketaccess.R
import com.amanpathak.bitbucketaccess.databinding.FragmentSettingBinding
import com.amanpathak.bitbucketaccess.utils.AppNavigator
import com.amanpathak.bitbucketaccess.utils.SharedPreferenceManager
import com.amanpathak.bitbucketaccess.utils.setSafeOnClickListener
import com.bumptech.glide.Glide

class SettingFragment : Fragment() {
    private lateinit var binding : FragmentSettingBinding
    private val viewModel : SettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        initViews()
        return binding.root
    }

    private fun initViews() {
        Glide.with(requireContext()).load(viewModel.profileDetail.value?.avatar).placeholder(R.drawable.ic_placeholder).into(binding.avatar)

        binding.logOut.setSafeOnClickListener {
            SharedPreferenceManager.clearAll()
            AppNavigator.navigateToSignIn(requireActivity(), null)
        }

    }

}