package io.github.yuk7.miuisearchbar.ui.extension

import java.net.URLEncoder

val String.urlEncoded: String
    get() = URLEncoder.encode(this, "UTF-8").replace("+", " ")