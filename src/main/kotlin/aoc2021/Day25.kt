package aoc2021

import DailyProblem
import java.io.File
import kotlin.time.ExperimentalTime

data class CucumberFloor(
    val map: Array<Char>,
    val width: Int,
    val height: Int) {

    private fun coordToIdx(x: Int, y: Int): Int {
        return x+width*y
    }
    fun get(x: Int, y: Int) = map[coordToIdx(x, y)]
    fun set(x: Int, y: Int, c: Char) {
        map[coordToIdx(x,y)] = c
    }

    fun print() {
        map.toList().chunked(width).forEach {
            it.forEach { c: Char -> print(c) }
            println()
        }
        println()
    }
}

fun parseCucumberMap(path: String): CucumberFloor {
    val input = File(path).readLines()


    return CucumberFloor(map=input.flatMap { line -> line.toList() }.toTypedArray(), width=input.first().length, height=input.size)
}

fun moveHorz(from: CucumberFloor, to: CucumberFloor): Boolean {
    var moveHappened = false
    for (y in (0 until to.height)) {
        for (x in (0 until to.width)) {
            val stuffAtPos = from.get(x,y)
            val stuffAtPosToRight = from.get((x+1).mod(from.width), y)
            val stuffAtPosToLeft = from.get((x-1).mod(from.width), y)
            var newChar: Char
            if (stuffAtPos == '>' && stuffAtPosToRight == '.') {
                newChar = '.'
                moveHappened = true
            } else if (stuffAtPos == '.' && stuffAtPosToLeft == '>') {
                newChar = '>'
                moveHappened = true
            } else {
                newChar = stuffAtPos
            }
            to.set(x,y,newChar)
        }
    }
    return moveHappened
}
fun moveVert(from: CucumberFloor, to: CucumberFloor): Boolean {
    var moveHappened = false
    for (x in (0 until to.width)) {
        for (y in (0 until to.height)) {
            val stuffAtPos = from.get(x,y)
            val stuffAtPosBelow = from.get(x, (y+1).mod(from.height))
            val stuffAtPosAbove = from.get(x, (y-1).mod(from.height))
            var newChar: Char
            if (stuffAtPos == 'v' && stuffAtPosBelow == '.') {
                newChar = '.'
                moveHappened = true
            } else if (stuffAtPos == '.' && stuffAtPosAbove == 'v') {
                newChar = 'v'
                moveHappened = true
            } else {
                newChar = stuffAtPos
            }
            to.set(x,y,newChar)
        }
    }
    return moveHappened
}

private fun move(map1: CucumberFloor, tempmap: CucumberFloor): Boolean {
    val moveHappenedHorz = moveHorz(from = map1, to = tempmap)
    val moveHappenedVert = moveVert(from = tempmap, to = map1)
    return moveHappenedHorz || moveHappenedVert
}

class Day25Problem(override val inputFilePath: String) : DailyProblem {
    override val number = 25

    override val name = "Sea Cucumber"

    override fun part1(): Long {
        val map1 = parseCucumberMap(inputFilePath)
        val height = map1.height
        val width = map1.width
        val tempmap = CucumberFloor(width = width, height = height, map = Array(width*height) { '.'})

        var steps = 0L
        while(true) {
            steps++
            if (!move(map1, tempmap)) {
                break
            }
        }

        return steps
    }

    override fun part2(): Long {
        // There is no real part 25 for today.
        return -1
    }
}


val day25Problem = Day25Problem("input/aoc2021/day25.txt")

@OptIn(ExperimentalTime::class)
fun main() {
    day25Problem.runBoth(100)
}