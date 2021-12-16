package aoc2021

import DailyProblem
import java.io.File

typealias PairCount = Map<String, Long>
typealias Rules = Map<String, List<String>>

/** Returns The count of occurences of pairs in the input, the production rules and the sequence as is */
fun parsePolyFile(path: String): Triple<PairCount, Rules, String> {
    val lines = File(path).readLines()
    val startChain = lines.first()
    val startingCounts: PairCount =
        startChain.windowed(2).groupingBy { it }.eachCount().mapValues { entry -> entry.value.toLong() }

    val rules = buildMap<String, List<String>> {
        lines.drop(2).map {
            val (rule, ins) = it.split(" -> ")
            put(rule, listOf("${rule[0]}$ins", "$ins${rule[1]}"))
        }
    }

    return Triple(startingCounts, rules, startChain)
}


fun applyRule(currentPairsCount: PairCount, rules: Rules): PairCount {

    val newCounts = currentPairsCount.toMutableMap()
    currentPairsCount.forEach { pairCount ->
        if (pairCount.key in rules) {
            newCounts.increase(pairCount.key, -pairCount.value) // Remove all such pairs...
            rules[pairCount.key]!!.forEach { newPair ->
                newCounts.increase(newPair, pairCount.value)    // ...and add all generated new pairs.
            }
        }
    }
    return newCounts.toMap()
}


private fun <K> MutableMap<K, Long>.increase(key: K, value: Long) {
    this[key] = getOrDefault(key, 0L) + value
}

class Day14Problem(override val inputFilePath: String) : DailyProblem {
    override val number = 14
    override val name = "Extended Polymerization"

    private lateinit var parsedProblem: Triple<Map<String, Long>, Map<String, List<String>>, String>

    private fun runRules(startCounts: PairCount, rules: Rules, startChain: String, steps: Int): Long {
        val afterApplyingRules = (1..steps).fold(startCounts) { acc, _ ->
            applyRule(acc, rules)
        }

        val charCounts: MutableMap<Char, Long> = mutableMapOf(
            // Add the first and last char of the star sequence so they don't get under-counted
            startChain.first() to 1L, startChain.last() to 1L)
        afterApplyingRules.forEach {
            it.key.forEach { char -> charCounts.increase(char, it.value) }
        }

        val res = (charCounts.maxOf { it.value } - charCounts.minOf { it.value })
        return res / 2
    }

    override fun commonParts() {
        this.parsedProblem = parsePolyFile(inputFilePath)
    }
    override fun part1(): Long {
        val (startCounts, rules, startChain) = parsedProblem
        return runRules(startCounts, rules, startChain, 10)
    }

    override fun part2(): Long {
        val (startCounts, rules, startChain) = parsedProblem
        return runRules(startCounts, rules, startChain, 40)
    }
}

val day14Problem = Day14Problem("input/aoc2021/day14.txt")
