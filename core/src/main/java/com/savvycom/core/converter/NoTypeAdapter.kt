package com.savvycom.core.converter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

class NoTypeAdapter : JsonAdapter<NoType?>() {

    @Synchronized
    override fun fromJson(reader: JsonReader): NoType? {
        val string: String? = reader.readJsonValue() as String?
        return NoType(data = string)
    }

    @Synchronized
    override fun toJson(writer: JsonWriter, value: NoType?) {
        writer.value(value?.data)
    }
}