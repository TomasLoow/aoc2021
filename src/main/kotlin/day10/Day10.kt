package day10

import DailyProblem
import java.io.File
import java.util.Stack


fun matchingParen(openingParen: Char): Char {
    return when (openingParen) {
        '(' -> ')'
        '[' -> ']'
        '{' -> '}'
        '<' -> '>'
        else -> throw Exception("Bad input")
    }
}


private fun isOpeningParen(char: Char): Boolean {
    return "<([{".contains(char)
}


fun mismatchScoreOfChar(char: Char): Long {
    return when (char) {
        ')' -> 3L
        ']' -> 57L
        '}' -> 1197L
        '>' -> 25137L
        else -> throw Exception("Bad input")
    }
}

private fun mismatchScoreOfLine(line: String): Long {
    val stack = Stack<Char>()
    line.forEach { char ->
        if (isOpeningParen(char)) {
            stack.push(char)
        } else {
            val currentOpening = stack.pop()
            if (matchingParen(currentOpening) != char) {
                return mismatchScoreOfChar(char)
            }
        }
    }
    return 0L
}


fun fixScoreOfChar(char: Char): Long {
    return when (char) {
        '(' -> 1L
        '[' -> 2L
        '{' -> 3L
        '<' -> 4L
        else -> throw Exception("Bad input")
    }
}


private fun fixScore(line: String): Long {
    val stack = Stack<Char>()
    line.forEach { char ->
        if (isOpeningParen(char)) {
            stack.push(char)
        } else {
            // This function will only be called on lines with no internal mismatches,
            // so just popping without checking for matches is fine
            stack.pop()
        }
    }
    var score = 0L
    while (stack.isNotEmpty()) {
        score = 5L * score + fixScoreOfChar(stack.pop()!!)
    }
    return score

}


class Problem(override val inputFilePath: String) : DailyProblem {

    override val number = 10

    override fun part1(): Long {
        return File(inputFilePath)
            .readLines()
            .sumOf {
                mismatchScoreOfLine(it)
            }
    }

    override fun part2(): Long {
        val sortedScores = File(inputFilePath)
            .readLines().filter { line -> mismatchScoreOfLine(line) == 0L }
            .map { fixScore(it) }
            .sorted()

        val midpoint = (sortedScores.size - 1) / 2
        return sortedScores[midpoint]
    }
}

val problem = Problem("input/day10.txt")
