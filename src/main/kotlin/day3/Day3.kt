package day3

import DailyProblem
import java.io.File

typealias BinaryArray = Array<Boolean>

fun parseBinaryFile(path: String): List<BinaryArray> {
    val lines: List<String> = File(path).readLines()
    return lines.map {
        val bs = BinaryArray(it.length) { false }
        for ((idx, char) in it.toCharArray().withIndex()) {
            if (char == '1') bs[idx] = true
        }
        bs
    }
}

enum class CountMode {
    MostCommon,
    LeastCommon,
}

fun findMostCommonByIndex(rows: List<BinaryArray>, idx: Int, mode: CountMode): Boolean {
    var countTrue = 0
    var countFalse = 0
    for (row in rows) {
        if (row[idx]) {
            countTrue++
        } else {
            countFalse++
        }
    }
    if (mode == CountMode.MostCommon) {
        return (countTrue >= countFalse)
    }
    return (countTrue < countFalse)
}

fun binaryToInt(bits: BinaryArray): Int {
    var res = 0
    for (bit in bits) {
        res *= 2
        if (bit) res++
    }
    return res
}

private fun bitCriteria(path: String, mode: CountMode): Int {
    var rows: List<BinaryArray> = parseBinaryFile(path)

    val rowLength: Int = rows[0].size

    for (idx in 0 until rowLength) {
        val target = findMostCommonByIndex(rows, idx, mode)
        rows = rows.filter { it[idx] == target }
        if (rows.size == 1) break
    }
    return binaryToInt(rows[0])
}

class Problem(override val inputFilePath: String) : DailyProblem {

    override val number = 3

    override fun part1(): Long {
        val input = parseBinaryFile(this.inputFilePath)

        val rowLength: Int = input[0].size

        val delta: BinaryArray = BinaryArray(rowLength) { false }
        val epsilon: BinaryArray = BinaryArray(rowLength) { false }

        for (idx in 0 until rowLength) {
            val mostCommon = findMostCommonByIndex(input, idx, CountMode.MostCommon)
            delta[idx] = mostCommon
            epsilon[idx] = !mostCommon
        }

        val deltaVal = binaryToInt(delta)
        val epsilonVal = binaryToInt(epsilon)

        return (deltaVal * epsilonVal).toLong()
    }

    override fun part2(): Long {
        val oxygen = bitCriteria(this.inputFilePath, CountMode.MostCommon)
        val co2 = bitCriteria(this.inputFilePath, CountMode.LeastCommon)
        return (oxygen * co2).toLong()
    }
}


val problem = Problem("input/day3.txt")
