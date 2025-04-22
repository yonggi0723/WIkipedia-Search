package com.yonggi.wikipediasearch.data.datasource.local

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DiskImageCache(private val context: Context) {

    companion object {
        private const val CACHE_TTL_MILLIS = 24 * 60 * 60 * 1000L // 1일
        private const val MAX_CACHE_SIZE = 20L * 1024 * 1024 // 20MB
    }

    private fun getFile(key: String): File =
        File(context.cacheDir, key.hashCode().toString())

    fun load(key: String): Bitmap? {
        val file = getFile(key)

        if (!file.exists()) return null

        val isExpired = System.currentTimeMillis() - file.lastModified() > Companion.CACHE_TTL_MILLIS
        if (isExpired) {
            file.delete()
            return null
        }

        return try {
            file.setLastModified(System.currentTimeMillis())
            BitmapFactory.decodeFile(file.absolutePath)
        } catch (e: IOException) {
            null
        }
    }

    fun save(key: String, bitmap: Bitmap) {
        try {
            ensureCacheLimit() // 기존 유지

            val file = getFile(key)
            FileOutputStream(file).use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
        } catch (e: IOException) {
            Log.e("DiskImageCache", "Failed to save image: ${e.message}")
        }
    }

    private fun ensureCacheLimit() {
        val files = context.cacheDir.listFiles() ?: return

        var totalSize = files.sumOf { it.length() }
        if (totalSize <= MAX_CACHE_SIZE) return

        val sortedFiles = files.sortedBy { it.lastModified() }

        for (file in sortedFiles) {
            if (file.delete()) {
                totalSize -= file.length()
            }
            if (totalSize <= MAX_CACHE_SIZE) break
        }
    }
}