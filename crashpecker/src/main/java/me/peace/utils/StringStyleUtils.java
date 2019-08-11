package me.peace.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;

public class StringStyleUtils {
    public static SpannableString format(Context context, String text, int style){
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new TextAppearanceSpan(context,style),0,text.length(),
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return spannableString;
    }
}
