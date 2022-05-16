package com.savvycom.core.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.savvycom.core.converter.IsoDateJsonAdapter
import com.savvycom.core.converter.NoType
import com.savvycom.core.converter.NoTypeAdapter
import java.util.*


/**
 * Global functions for Parsing (parse json, parse date, ...)
 */
class ParseUtil {
    companion object {
        @JvmStatic
        fun <T> fromJson(json: String?, type: Class<T>): T? =
            json?.let {
                val jsonAdapter = moshi.adapter<T>(type).lenient().nullSafe()
                return try {
                    jsonAdapter.fromJson(json)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            } ?: run {
                null
            }

        @JvmStatic
        inline fun <reified T : Any> fromJson(json: String?): T? =
            json?.let {
                val jsonAdapter = moshi.adapter<T>(T::class.java).lenient().nullSafe()
                return try {
                    jsonAdapter.fromJson(json)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            } ?: run {
                null
            }

        @JvmStatic
        inline fun <reified T : Any> toJson(t: T?): String? =
            t?.let {
                try {
                    val jsonAdapter = moshi.adapter<T>(T::class.java).lenient().nullSafe()
                    jsonAdapter.toJson(t)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            } ?: run {
                null
            }

        val moshi: Moshi by lazy {
            Moshi.Builder()
                .add(NoType::class.java, NoTypeAdapter().lenient())
                .add(Date::class.java, IsoDateJsonAdapter())
                .build()
        }

        @JvmStatic
        inline fun <reified T : Any> toJsonList(t: List<T>?): String? =
            t?.let {
                try {
                    val type = Types.newParameterizedType(List::class.java, T::class.java)
                    val jsonAdapter = moshi.adapter<List<T>>(type).lenient().nullSafe()
                    jsonAdapter.toJson(t)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            } ?: run {
                null
            }

        @JvmStatic
        fun <T> parseList(json: String?, type: Class<T>): List<T>? {
            return json?.let {
                val listMyData = Types.newParameterizedType(List::class.java, type)
                val jsonAdapter = moshi.adapter<List<T>>(listMyData).lenient().nullSafe()
                try {
                    jsonAdapter.fromJson(json)
                } catch (e: Exception) {
                    null
                }
            }
        }

        @JvmStatic
        inline fun <reified T : Any> parseList(json: String?): List<T>? {
            return json?.let {
                val listMyData = Types.newParameterizedType(List::class.java, T::class.java)
                val jsonAdapter = moshi.adapter<List<T>>(listMyData).lenient().nullSafe()
                try {
                    jsonAdapter.fromJson(json)
                } catch (e: Exception) {
                    null
                }
            }
        }

        @JvmStatic
        fun <K, V> parseMap(json: String?, typeKey: Class<K>, typeValue: Class<V>): Map<K, V>? {
            return json?.let {
                val listMyData = Types.newParameterizedType(Map::class.java, typeKey, typeValue)
                val jsonAdapter = moshi.adapter<Map<K, V>>(listMyData).lenient().nullSafe()
                try {
                    jsonAdapter.fromJson(json)
                } catch (e: Exception) {
                    null
                }
            }
        }

        @JvmStatic
        fun <K, V> parseMapList(json: String?, typeKey: Class<K>, typeValue: Class<V>): Map<K, List<V>>? {
            return json?.let {
                val listMyData = Types.newParameterizedType(Map::class.java, typeKey, List::class.java, typeValue)
                val jsonAdapter = moshi.adapter<Map<K, List<V>>>(listMyData).lenient().nullSafe()
                try {
                    jsonAdapter.fromJson(json)
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

}