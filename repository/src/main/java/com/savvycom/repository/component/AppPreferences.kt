package com.savvycom.repository.component

import android.app.Application
import android.content.Context
import com.savvycom.core.utils.ParseUtil

const val PREF_NAME = "pref_AndroidTest01"

class AppPreferences(private val app: Application) {
    private val sharedPreferences = app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private enum class KEY(val value: String) {
        TOKEN("token"),

    }


    @Suppress("IMPLICIT_CAST_TO_ANY")
    private inline fun <reified T : Any> get(key: KEY, default: T?): T? =
        sharedPreferences.let {
            when (default) {
                is Boolean -> it.getBoolean(key.value, default)
                is String -> it.getString(key.value, default)
                is Float -> it.getFloat(key.value, default)
                is Int -> it.getInt(key.value, default)
                is Long -> it.getLong(key.value, default)
                else -> it.getString(key.value, null)?.let { json ->
                    ParseUtil.fromJson(json, T::class.java)
                } ?: run {
                    null
                }
            } as T?
        }

    private inline fun <reified T : Any> put(key: KEY, t: T?) {
        sharedPreferences.edit().apply {
            when (t) {
                is Boolean -> putBoolean(key.value, t)
                is String -> putString(key.value, t)
                is Float -> putFloat(key.value, t)
                is Int -> putInt(key.value, t)
                is Long -> putLong(key.value, t)
                else -> putString(key.value, ParseUtil.toJson(t))
            }.apply()
        }
    }

    private inline fun <reified T : Any> getObject(key: KEY): T? =
        sharedPreferences.let {
            it.getString(key.value, null)?.let { json ->
                ParseUtil.fromJson(json, T::class.java)
            } ?: run {
                null
            }
        }

    private inline fun <reified T : Any> putList(key: KEY, t: List<T>?) {
        sharedPreferences.edit().apply {
            putString(key.value, ParseUtil.toJsonList(t))
        }.apply()
    }

    private inline fun <reified T : Any> getListObject(key: KEY): List<T>? =
        sharedPreferences.let {
            it.getString(key.value, null)?.let { json ->
                ParseUtil.parseList(json, T::class.java)
            } ?: run {
                null
            }
        }
}