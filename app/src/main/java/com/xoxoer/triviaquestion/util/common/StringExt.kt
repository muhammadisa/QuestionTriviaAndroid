package com.xoxoer.triviaquestion.util.common

import java.net.URLDecoder

fun String.toDecodedURL(): String {
    return URLDecoder.decode(this, "UTF-8")
}