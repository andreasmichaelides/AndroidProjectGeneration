package codegen.tools

import com.google.common.base.CaseFormat


fun String.toLowerUnderscore(): String {
    return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this)
}

fun String.toLowerCameCase(): String {
    return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, this)
}