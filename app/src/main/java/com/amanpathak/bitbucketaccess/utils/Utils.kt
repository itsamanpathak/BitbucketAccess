package com.amanpathak.bitbucketaccess.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

object Utils {

    fun convertDate_DD_MM_YYYY(dateString: String) : String {
            if(dateString.isNullOrEmpty()) return ""

            val date = dateString.split(".")

            var spf =  SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss").parse(date[0])
            val simpleDateFormat = SimpleDateFormat("dd MMM YYYY")
            return simpleDateFormat.format(spf)
    }



}