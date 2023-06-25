package com.amanpathak.bitbucketaccess.ui.home

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.amanpathak.bitbucketaccess.R
import com.amanpathak.bitbucketaccess.databinding.ActivityHomeBinding
import com.amanpathak.bitbucketaccess.databinding.ActivityRepoDetailBinding
import com.amanpathak.bitbucketaccess.model.RepoModel
import com.amanpathak.bitbucketaccess.utils.Utils
import com.amanpathak.bitbucketaccess.utils.setSafeOnClickListener
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView

class RepoDetailScreen : AppCompatActivity() {


    companion object {
        val REPO_DETAIL_MODEL = "repo_detail_model"
    }


    private lateinit var binding: ActivityRepoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoDetailBinding.inflate(layoutInflater)
        initViews()
        setContentView(binding.root)

    }

    private fun initViews() {
        val hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.back_button_anim);
        hyperspaceJump.fillAfter = true;


        val bundle = intent.extras
        bundle?.let { bdle ->

            val model : RepoModel = bdle.getParcelable(REPO_DETAIL_MODEL)!!


            Glide.with(this).load(model.avatar).placeholder(R.drawable.ic_placeholder).into(binding.avatar)
            binding.let { it ->
                it.desc.text = model.des
                it.createdOn.text = "Created on :${Utils.convertDate_DD_MM_YYYY(model.createdOn)} "
                it.repoName.text = model.name
                it.isPrivate.text = "Is Private : ${model.isPrivate}"
                it.workspace.text = "Workspace : ${model.workspace}"

                it.back.startAnimation(hyperspaceJump)
                it.back.setSafeOnClickListener {
                    finish()
                }


            }


        }

    }


}