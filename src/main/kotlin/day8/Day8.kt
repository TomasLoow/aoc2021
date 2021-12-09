package day8

import DailyProblem
import java.io.File

typealias ActiveWires = Set<Char>

fun strToWires(data: String): ActiveWires {
    return data.toCharArray().toSet()
}

fun parseDisplaysFile(path: String): List<Pair<List<ActiveWires>, List<ActiveWires>>> {
    return File(path)
        .readLines()
        .map { line ->
            val (clues, displays) = line.split(" | ")
            Pair(clues
                    .split(" ")
                    .map { strToWires(it) },
                displays
                    .split(" ")
                    .map { strToWires(it) }
            )
        }
}

fun solveLine(line: Pair<List<ActiveWires>, List<ActiveWires>>): List<Int> {
    val (clues, displays) = line

    val numToWiresMap: MutableMap<Int, ActiveWires> = mutableMapOf()
    numToWiresMap[1] = clues.find { it.size == 2 }!!
    numToWiresMap[4] = clues.find { it.size == 4 }!!
    numToWiresMap[7] = clues.find { it.size == 3 }!!
    numToWiresMap[8] = clues.find { it.size == 7 }!!

    // Determine the numbers with six segments lit (0, 6 and 9)
    val sixSegments = clues.filter { it.size == 6 }

    for (sixClue in sixSegments) {
        if (sixClue.containsAll(numToWiresMap[4]!!)) // Only the six fully contains the four
            numToWiresMap[9] = sixClue
        else if (sixClue.containsAll(numToWiresMap[1]!!)) // Only the zero fully contains the one
            numToWiresMap[0] = sixClue
        else
            numToWiresMap[6] = sixClue
    }

    // Determine the numbers with five segments lit (2,3 and 5)
    val fiveSegments = clues.filter { it.size == 5 }

    for (fiveClue in fiveSegments) {
        if (fiveClue.containsAll(numToWiresMap[1]!!)) // Only the three fully contains the one
            numToWiresMap[3] = fiveClue
        else if (numToWiresMap[6]!!.containsAll(fiveClue)) // Only the five is contained in the six
            numToWiresMap[5] = fiveClue
        else
            numToWiresMap[2] = fiveClue
    }
    // We now know all digits. Reverse the map and parse the entries from the RHS
    val reversed: Map<ActiveWires, Int> = numToWiresMap.entries.associate { entry -> Pair(entry.value, entry.key) }
    return displays.map { reversed[it]!! }
}


class Problem(override val inputFilePath: String) : DailyProblem {
    override val number = 8

    private fun Iterable<Int>.parseDecimal() : Int{
        var acc = 0
        forEach {
            acc = acc*10 + it
        }
        return acc
    }

    override fun part1(): Long {
        val lines = parseDisplaysFile(inputFilePath)

        val interestingDigits = setOf(1, 4, 7, 8)

        val solved: List<List<Int>> = lines.map { solveLine(it) }
        return solved.sumOf { digits ->
            digits.count { digit -> interestingDigits.contains(digit) }
        }.toLong()
    }
    override fun part2(): Long {
        val lines = parseDisplaysFile(inputFilePath)
        val solved = lines.map { solveLine(it) }
        val converted = solved.map { digits -> digits.parseDecimal() }
        return converted.sum().toLong()
    }
}

val problem = Problem("input/day8.txt")