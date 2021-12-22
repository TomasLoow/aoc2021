package aoc2021

import DailyProblem
import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.time.ExperimentalTime

fun parseReactorFile(path: String) : List<SignedRect> {
    val re = Regex("(on|off) x=(-?[0-9]+)\\.\\.(-?[0-9]+),y=(-?[0-9]+)\\.\\.(-?[0-9]+),z=(-?[0-9]+)\\.\\.(-?[0-9]+)")
    return File(path).readLines().map { line ->
        val reResult = re.find(line)!!.groupValues

        val sign: Sign = if (reResult[1] == "on") 1 else -1
        SignedRect(sign, Rectangle(
            (reResult[2].toInt()..reResult[3].toInt()),
            (reResult[4].toInt()..reResult[5].toInt()),
            (reResult[6].toInt()..reResult[7].toInt()),
        ))
    }
}


data class Rectangle(
        val xrange: IntRange,
        val yrange: IntRange,
        val zrange: IntRange,
    ) {
    fun intersection(other: Rectangle): Rectangle? {
        val newXrange =  xrange.overlap(other.xrange)?: return null
        val newYrange =  yrange.overlap(other.yrange)?: return null
        val newZrange =  zrange.overlap(other.zrange)?: return null
        return Rectangle(newXrange, newYrange, newZrange)
    }
    fun size():Long {
        return xrange.length() * yrange.length() * zrange.length()
    }
}

private fun IntRange.length(): Long {
    return (last-first+1).toLong()

}

private fun IntRange.overlap(other: IntRange): IntRange? {
    val r = (max(this.first, other.first)..min(this.last, other.last))
    if (r.isEmpty()) return null else return r
}


typealias Sign = Int

typealias SignedRect = Pair<Sign, Rectangle>

class Day22Problem(override val inputFilePath: String) : DailyProblem {
    override val number = 22
    override val name = "Reactor Reboot"

    private lateinit var inputSquares: List<SignedRect>

    override fun commonParts() {
        inputSquares = parseReactorFile(inputFilePath)
    }

    override fun part1(): Long {
        return combineRectsAndCountActive(inputSquares.filter {
            it.second.xrange.first in (-50 .. 50) &&
            it.second.xrange.last in (-50 .. 50) &&
            it.second.yrange.first in (-50 .. 50) &&
            it.second.yrange.last in (-50 .. 50) &&
            it.second.zrange.first in (-50 .. 50) &&
            it.second.zrange.last in (-50 .. 50)
        })

    }

    override fun part2(): Long {
        return combineRectsAndCountActive(inputSquares)
    }

    private fun combineRectsAndCountActive(rects: List<SignedRect>): Long {
        val processedRectangles = mutableListOf<SignedRect>()
        for (insertingRect in rects) {
            val newRects = mutableListOf<SignedRect>()
            for (existingRec in processedRectangles) {
                val intersectrect = insertingRect.second.intersection(existingRec.second) ?: continue
                newRects.add(SignedRect(-existingRec.first, intersectrect))
            }
            processedRectangles.addAll(newRects)
            if (insertingRect.first == 1) processedRectangles.add(insertingRect)
        }
        return processedRectangles.sumOf { it.first * it.second.size() }
    }
}

val day22Problem = Day22Problem("input/aoc2021/day22.txt")

@OptIn(ExperimentalTime::class)
fun main() {
    day22Problem.runBoth(10)
}