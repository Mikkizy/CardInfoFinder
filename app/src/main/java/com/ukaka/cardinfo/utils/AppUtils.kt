package com.ukaka.cardinfo.utils

import android.app.Activity
import android.view.Gravity
import androidx.core.content.res.ResourcesCompat
import com.ukaka.cardinfo.R
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.util.Locale

object AppUtils {
    fun showToast(context: Activity, message: String?, style: MotionToastStyle, position: Int = Gravity.CENTER_HORIZONTAL) {
        try {
            MotionToast.createColorToast(
                context,
                "Cupid",
                message!!,
                style,
                position,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(context, www.sanju.motiontoast.R.font.helvetica_regular)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun String.capitalizeFirstLetter(): String {
        return this.lowercase(Locale.ROOT).replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
    }
}