package cc.bear3.util.utils.regex

import java.util.regex.Pattern

/**
 *
 * @author TT
 * @since 2021-3-1
 */

const val REGEX_MOBILE = "(13|14|15|18|17|19)[0-9]{9}"
const val REGEX_EMAIL = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}"
const val REGEX_PASSWORD = "^(?![0-9]+\$)(?![a-zA-Z]+\$).{8,15}\$"

fun String.isMatchRule(regex: String): Boolean {
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}