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

class Problem(override val inputFilePath: String) : DailyProblem {
    override val number = 2

    override fun part1(): Int {
        val commands = parseCommandFile(this.inputFilePath)

        var x = 0
        var depth = 0
        for (command in commands) {
            val type = command.first
            val arg = command.second
            when (type) {
                Command.Forward -> x += arg
                Command.Up -> depth -= arg
                Command.Down -> depth += arg
            }
        }
        return x * depth
    }

    override fun part2(): Int {
        val commands = parseCommandFile(this.inputFilePath)

        var x = 0
        var delta = 0
        var depth = 0
        for (command in commands) {
            val type = command.first
            val arg = command.second
            when (type) {
                Command.Forward -> {
                    x += arg
                    depth += delta * arg
                }
                Command.Up -> delta -= arg
                Command.Down -> delta += arg
            }
        }
        return x * depth
    }
}

val problem = Problem("input/day2.txt")