package com.example.chatroom

import android.content.Context
import android.content.SharedPreferences

object ShareUtil {

    private var sharePreference: SharedPreferences? = null

    private fun getSharedPreferences(context: Context): SharedPreferences {

        if(sharePreference == null) {

            sharePreference = context.getSharedPreferences("chat", Context.MODE_PRIVATE)
        }

        return sharePreference!!
    }

    fun putString(key: String, value: String, context: Context) {

        val editor = getSharedPreferences(context).edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String, context: Context): String? {

        val spf = getSharedPreferences(context)
        return spf.getString(key, "")
    }

    fun clearSharePref(context: Context) {

        val editor = getSharedPreferences(context).edit()
        editor.clear().apply()
    }
}