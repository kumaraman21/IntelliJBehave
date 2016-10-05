@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.regex_testing

import org.jbehave.core.parsers.StepMatcher

/**
 * Created by Rodrigo Quesada on 04/10/16.
 */
private object StepPatterns {
    object Plain {
        val VARIABLE = "(.*)"
    }

    object Regex {
        val SPACE = Regex("\\s+")
    }
}

internal class OptimizedStepMatcher(stepMatcher: StepMatcher) : StepMatcher by stepMatcher {

    private val matchesRegexes: List<Regex>
    private val findRegexes: List<Regex>

    init {
        val patterns = stepMatcher.pattern().resolved().split(StepPatterns.Plain.VARIABLE)
        fun List<String>.toRegexes() = asSequence()
                .map { it.replace(StepPatterns.Regex.SPACE, "\\s") }
                .map(::Regex)
                .toList()

        findRegexes = patterns.filter(String::isNotEmpty).toRegexes()

        matchesRegexes = patterns.toRegexes().mapIndexed { i, regex ->
            when (i) {
                0 -> Regex("^${regex.pattern}")
                patterns.lastIndex -> Regex("${regex.pattern}$")
                else -> regex
            }
        }
    }

    //TODO implement catching optimization strategy

    override fun matches(text: String): Boolean = text.preprocessThenFind(matchesRegexes)

    override fun find(text: String): Boolean = text.preprocessThenFind(findRegexes)

    private inline fun String.preprocessThenFind(regexes: List<Regex>) = trimSpaces().find(regexes)

    private fun String.find(regexes: List<Regex>, textIndex: Int = 0, regexIndex: Int = 0)
            : Boolean = regexIndex == regexes.size || textIndex < length && run {
        val matchResult = regexes[regexIndex].find(this, textIndex)
        matchResult != null && find(regexes, matchResult.range.endInclusive + 1, regexIndex + 1)
    }
}

//region Utils
private inline fun String.trimSpaces() = replace(StepPatterns.Regex.SPACE, " ")
//endregion
