package com.ashish.cleanjetpackhilt.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

fun <T> String?.toDataList(clazz: Class<T>): MutableList<T> {
    kotlin.runCatching {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter<List<T>>(
            Types.newParameterizedType(
                List::class.java,
                clazz
            )
        )
        return  adapter.fromJson(this.orEmpty())?.toMutableList() ?: arrayListOf()
    }.getOrElse {
        return arrayListOf()
    }
}

inline fun <reified T> List<T>?.convertListToJson(): String {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val listType = Types.newParameterizedType(List::class.java, T::class.java)
    val adapter = moshi.adapter<List<T>>(listType)

    return adapter.toJson(this)
}