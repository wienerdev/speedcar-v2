package com.preko.speedcarv2.utils

import android.content.Context
import android.content.SharedPreferences
import com.preko.speedcarv2.R

class Prefs {

    private var context: Context? = null
    private var sharedPreferences: SharedPreferences? = null
    private lateinit var editor: SharedPreferences.Editor

    fun Prefs(context: Context) {
        this.context = context
        sharedPreferences =
            context.getSharedPreferences(context.resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
    }


    fun setInt(key: String?, value: Int) {
        editor!!.putInt(key, value)
        editor!!.apply()
    }

    fun setString(key: String?, value: String?) {
        editor!!.putString(key, value)
        editor!!.apply()
    }

    fun setPremium(value: Int) {
        editor!!.putInt("Premium", value)
        editor!!.apply()
    }


    fun setBoolean(key: String?, value: Boolean) {
        editor!!.putBoolean(key, value)
        editor!!.apply()
    }

    fun getBoolean(key: String?, def: Boolean): Boolean {
        return sharedPreferences!!.getBoolean(key, def)
    }

    fun getInt(key: String?, def: Int): Int {
        return sharedPreferences!!.getInt(key, def)
    }


    fun getString(key: String?, def: String?): String? {
        return sharedPreferences!!.getString(key, def)
    }

    fun getPremium(): Int {
        return sharedPreferences!!.getInt("Premium", 0)
    }
}