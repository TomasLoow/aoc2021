package aoc2021

import DailyProblem
import parserCombinators.*
import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.time.ExperimentalTime

private fun parseTrickShot(path: String): List<Int> {
    val line = File(path).readLines().single().toList()

    val pminX = literal("target area: x=".toList()) thenDo ::parseInt
    val pmaxX = literal("..".toList()) thenDo ::parseInt
    val pminY = literal(", y=".toList()) thenDo ::parseInt
    val pmaxY = literal("..".toList()) thenDo ::parseInt
    val parser: Parser<Char,List<Int>> = parseList(listOf(pminX, pmaxX, pminY, pmaxY))


    val (res, _) = parser(line)
    return res
}

class Day17Problem(override val inputFilePath: String) : DailyProblem {
    override val number: Int = 17
    override val name: String = "Trick Shot"

    var minX:Int = 0
    var minY:Int = 0
    var maxX:Int = 0
    var maxY:Int = 0

    override fun commonParts() {
       val parsed = parseTrickShot(inputFilePath)
       minX = parsed.component1()
       maxX = parsed.component2()
       minY = parsed.component3()
       maxY = parsed.component4()
    }

    override fun part1(): Long {

        return (minY.absoluteValue*(minY.absoluteValue - 1)/2).toLong()
    }

    override fun part2(): Long {

        val maxDx = maxX
        val minDx = sqrt(((2*minX).toDouble())).roundToInt()
        val maxDy = minY.absoluteValue
        val minDy = minY

        var counter = 0L
        (minDy .. maxDy).forEach{ startDy ->
            (minDx .. maxDx).forEach { startDx ->
                if (checkTrajectory(startDx, startDy)) counter++
            }

            }
        return counter
    }

    private fun checkTrajectory(startDx: Int, startDy: Int): Boolean {
        var x = 0
        var y = 0
        var dx = startDx
        var dy = startDy
        while (x <= maxX && y >= minY) {
            if (x in (minX..maxX) && y in (minY..maxY)) {
                return true
            }
            x += dx
            y += dy
            dy--
            dx = if (dx > 0) (dx - 1) else 0
        }
        return false
    }

}
val day17Problem = Day17Problem("input/aoc2021/day17.txt")

@OptIn(ExperimentalTime::class)
fun main() {
    day17Problem.runBoth(1000)
}