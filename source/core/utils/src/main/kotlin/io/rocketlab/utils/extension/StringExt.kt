package io.rocketlab.utils.extension

import io.rocketlab.utils.BULLET
import io.rocketlab.utils.COMMA
import io.rocketlab.utils.DOT
import io.rocketlab.utils.EMPTY
import io.rocketlab.utils.SPACE
import java.util.Locale
import java.util.regex.Pattern

private const val MID_DOT = "\u00B7"
private const val FOUR_BULLETS = "$BULLET$BULLET$BULLET$BULLET"
private const val NON_LETTERS_PATTERN = "[^A-Za-zа-яА-Я ]"

@Suppress("MaxLineLength")
private val EMAIL_PATTERN by lazy { Pattern.compile("[a-zA-Zа-яёА-ЯЁ0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Zа-яёА-ЯЁ0-9][a-zA-Zа-яёА-ЯЁ0-9\\-]{0,64}(\\.[a-zA-Zа-яёА-ЯЁ0-9][a-zA-Zа-яёА-ЯЁ0-9\\-]{0,25})+") }

fun maskLoginText(string: String?): String? {
    return when {
        string.isNullOrEmpty() -> string
        string.length < 4 -> FOUR_BULLETS
        else -> string.substring(0, 1) + FOUR_BULLETS + string.substring(string.length - 2)
    }
}

fun String?.maskLast4Symbols(): String? {
    var text = this
    if (text.isNullOrEmpty()) {
        return text
    }
    if (text.length > 4) {
        text = text.substring(text.length - 4)
    }
    text = text.removeNotDigitsAndSigns()
    text = MID_DOT + MID_DOT + text
    return text
}

fun String?.removeSpaces(): String? = this?.replace(Regex("\\s"), EMPTY) ?: this

fun String?.removeNotDigits(): String? = this?.replace(Regex("[^+0-9.-]"), EMPTY) ?: this

fun String.removeNotLetters(): String = this.replace(Regex(NON_LETTERS_PATTERN), EMPTY)

fun String?.removeNotDigitsAndSigns(): String? = this?.replace(Regex("[^0-9]"), EMPTY) ?: this

fun CharSequence?.removeChar(index: Int): CharSequence? {
    return this?.let {
        it.subSequence(0, index) + it.subSequence(index + 1, it.length)
    } ?: this
}

operator fun CharSequence.plus(sequence: CharSequence): String = toString() + sequence

fun String?.normalizeAmountValue(fallback: String): String {
    return if (!this.isNullOrEmpty()) {
        this.trim().removeSpaces()!!.replace(COMMA, DOT)
    } else {
        fallback
    }
}

private fun StringBuilder.notPathOfWord(symbol: String): Boolean {
    val inStartOfWord = Regex("""\\*$symbol[a-zA-Z0-9]+""").find(this)?.value
    val inEndOfWord = Regex("""[a-zA-Z0-9]+$symbol\\*""").find(this)?.value
    return inStartOfWord.isNullOrEmpty() && inEndOfWord.isNullOrEmpty()
}

fun StringBuilder.replaceAll(from: String, to: String) {
    var index = indexOf(from)
    while (index != -1) {
        replace(index, index + from.length, to)
        index += to.length
        index = indexOf(from, index)
    }
}

fun String?.isEmailValid() = !this.isNullOrEmpty() && EMAIL_PATTERN.matcher(this).matches()

fun String.capitalizeEveryWord(): String {
    val wordsInString = this.lowercase().split(SPACE)
    val capitalizedString = StringBuilder()

    wordsInString.forEach {
        capitalizedString.append(it.capitalize() + SPACE)
    }
    return capitalizedString.toString().trim()
}

fun String.capitalize(): String {
    return replaceFirstChar {
        if (it.isLowerCase()) {
            it.titlecase(Locale.getDefault())
        } else {
            it.toString()
        }
    }
}