package com.example.azbukavkusatest.utils

import android.os.Build
import android.text.Html

object HtmlDecoder {
    fun decode(html: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString()
        else Html.fromHtml(html).toString()
}