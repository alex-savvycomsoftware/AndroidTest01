package com.savvycom.core.converter

import android.os.Build
import androidx.annotation.RequiresApi
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import org.threeten.bp.DateTimeUtils
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class IsoDateJsonAdapter : JsonAdapter<Date?>() {
    @RequiresApi(Build.VERSION_CODES.O)
    @Synchronized
    override fun fromJson(reader: JsonReader): Date? {
        val string: String? = reader.readJsonValue() as String?
        try {

            string?.let {
                val localDateTime = LocalDateTime.parse(string, DateTimeFormatter.ISO_DATE_TIME)
                val zdt = localDateTime.atZone(ZoneOffset.UTC)
                return Date(zdt.toInstant().toEpochMilli())
            }
        } catch (e: Exception) {
            try {
                string?.let {
                    val tIndex = it.indexOf("T")
                    val date = if (tIndex > 0) it.substring(0, tIndex) else it
                    val localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
                    val zdt = localDate.atStartOfDay(ZoneOffset.UTC)
                    return Date(zdt.toInstant().toEpochMilli())
                }
            } catch (f: Exception) {
                f.printStackTrace()
            }
        }
        return null
    }

    @Synchronized
    override fun toJson(writer: JsonWriter, value: Date?) {
        try {
            val string = value?.let { DateTimeUtils.toInstant(value).toString() }
            writer.value(string)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}