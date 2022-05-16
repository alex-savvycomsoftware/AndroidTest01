package com.savvycom.core.utils

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.LocaleList
import java.util.*

class LocaleUtil {
    companion object {

        @Suppress("DEPRECATION")
        fun wrap(context : Context, language : String): ContextWrapper {
            val res = context.resources
            val configuration = res.configuration
            val newLocale = Locale(language)
            Locale.setDefault(newLocale)
            configuration.locale = newLocale

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.setLocale(newLocale)
                val localeList = LocaleList(newLocale)
                LocaleList.setDefault(localeList)
                configuration.setLocales(localeList)
                res.updateConfiguration(configuration, res.displayMetrics)

            } else {
                configuration.setLocale(newLocale)
                res.updateConfiguration(configuration, res.displayMetrics)
            }

            context.applicationContext.resources.updateConfiguration(
                configuration,
                res.displayMetrics
            )

            return ContextWrapper(context)
        }
    }
}