package com.savvycom.core.utils

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*
import javax.security.cert.CertificateException

@GlideModule
class UnsafeOkHttpGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.replace(
            GlideUrl::class.java, InputStream::class.java,
            OkHttpUrlLoader.Factory(unsafeOkHttpClient)
        )
    }
}

val unsafeOkHttpClient: OkHttpClient = try {
    // Create a trust manager that does not validate certificate chains
    val trustAllCerts: Array<TrustManager> = arrayOf(
        object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(
                chain: Array<X509Certificate?>?,
                authType: String?
            ) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(
                chain: Array<X509Certificate?>?,
                authType: String?
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }

        }
    )

    // Install the all-trusting trust manager
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, SecureRandom())

    // Create an ssl socket factory with our all-trusting manager
    val sslSocketFactory = sslContext.socketFactory
    val builder = OkHttpClient.Builder()
    builder.sslSocketFactory(
        sslSocketFactory,
        trustAllCerts[0] as X509TrustManager
    )
    builder.hostnameVerifier(object: HostnameVerifier{
        override fun verify(p0: String?, p1: SSLSession?): Boolean {
            return true
        }
    })
    builder.build()
} catch (e: Exception) {
    throw RuntimeException(e)
}