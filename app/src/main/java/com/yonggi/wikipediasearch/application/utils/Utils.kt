package com.yonggi.wikipediasearch.application.utils

import android.content.res.Resources

object Utils {
    fun extract(caption: String): String {
        return caption.split(" ")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .take(3)
            .joinToString(" ")
    }


    fun Int.dpToPx(): Int =
        (this * Resources.getSystem().displayMetrics.density).toInt()
}