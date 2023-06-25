package com.amanpathak.bitbucketaccess.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter


object BindingUtils {

    @BindingAdapter("setAdapter")
    fun setAdapter(recyclerView: RecyclerView, adapter: Adapter<RecyclerView.ViewHolder>) {
        recyclerView.adapter = adapter
    }


}