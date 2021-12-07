package day2

import DailyProblem
import java.io.File
import java.util.*


enum class Command {
    Forward,
    Down,
    Up,
}


fun parseCommandFile(path: String): List<Pair<Command, Int>> {
    val lines: List<String> = File(path).readLines()

    return lines.map {

        val splitLine: List<String> = it.split(" ")
        val command = when (splitLine[0]) {
            "forward" -> Command.Forward
            "up" -> Command.Up
            "down" -> Command.Down
            else -> throw InputMismatchException()
        }

        val arg = splitLine[1].toInt()

        Pair(command, arg)
    }
}

/* Move one step with the rules from part 1 of the problem */
fun updatePos(pos: Pair<Int, Int>, cmd: Pair<Command, Int>): Pair<Int, Int> {
    val (command, arg) = cmd
    val (x, depth) = pos
    return when (command) {
        Command.Forward -> Pair(x + arg, depth)
        Command.Up -> Pair(x, depth - arg)
        Command.Down -> Pair(x, depth + arg)
    }
}


/* Move one step with the rules from part 2 of the problem */
fun updatePosWithDelta(pos: Triple<Int, Int, Int>, cmd: Pair<Command, Int>): Triple<Int, Int, Int> {
    val (command, arg) = cmd
    val (x, depth, delta) = pos
    return when (command) {
        Command.Forward -> Triple(x + arg, depth + delta * arg, delta)
        Command.Up -> Triple(x, depth, delta - arg)
        Command.Down -> Triple(x, depth, delta + arg)
    }
}


class Problem(override val inputFilePath: String) : DailyProblem {
    override val number = 2

    override fun part1(): Long {
        val commands = parseCommandFile(this.inputFilePath)
        val initialState = Pair(0, 0)
        val (x, depth) = commands.fold(initialState) { pos, cmd -> updatePos(pos, cmd) }

        return (x * depth).toLong()
    }

    override fun part2(): Long {
        val commands = parseCommandFile(this.inputFilePath)
        val initialState = Triple(0, 0, 0)
        val (x, depth, _) = commands.fold(initialState) { pos, cmd -> updatePosWithDelta(pos, cmd) }

        return (x * depth).toLong()
    }
}

val problem = Problem("input/day2.txt")