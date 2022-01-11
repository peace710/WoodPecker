package me.peace.utils

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.TextAppearanceSpan

object StringStyleUtils {
    fun format(context: Context?, text: String, style: Int): SpannableString {
        val spannableString = SpannableString(text)
        spannableString.setSpan(
            TextAppearanceSpan(context, style), 0, text.length,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        return spannableString
    }
}