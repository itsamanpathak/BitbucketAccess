package com.amanpathak.bitbucketaccess.utils

import android.content.Context
import android.content.SharedPreferences
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.amanpathak.bitbucketaccess.model.SignInModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object SharedPreferenceManager {

    //KEYS
    const val KEY_IS_SIGNED_IN = "is_signed_in"
    const val KEY_USERNAME = "username"
    const val KEY_PASSWORD = "password"
    const val KEY_PROFILE_DETAIL = "profile_detail"

    private var sharedPreference : SharedPreferences? = null
    private var editor : SharedPreferences.Editor? = null



    fun initialize(context: Context){
        if(sharedPreference == null){

            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            sharedPreference = EncryptedSharedPreferences.create(context, "App",masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
            editor = sharedPreference?.edit()
        }
    }

    fun setString(key : String, value: String) {
        editor?.putString(key, value)?.apply()
    }


    fun getString(key : String): String {
        return sharedPreference?.getString(key,"")!!
    }

    fun setBoolean(key : String, value: Boolean) {
        editor?.putBoolean(key, value)?.apply()
    }

    fun getBoolean(key : String): Boolean {
        return sharedPreference?.getBoolean(key,false) == true
    }

    fun <T> setObject(key : String, value: T) {
        val json = Gson().toJson(value)
        editor?.putString(key, json)?.apply()
    }

    fun <T> retrieveObject(key: String, type : Class<T>) : T {
        val json = sharedPreference?.getString(key,"")
        return Gson().fromJson<T>(json, type)
    }

    fun setInt(key : String, value: Int) {
        editor?.putInt(key, value)?.apply()
    }

    fun getInt(key : String): Int {
        return sharedPreference?.getInt(key, -1)!!
    }


    fun clearAll(){
        sharedPreference?.edit()?.clear()?.apply()
    }





}