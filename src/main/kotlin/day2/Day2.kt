package day2

import DailyProblem
import java.io.File


enum class Command {
    Forward,
    Down,
    Up,
}

typealias CommandLine = Pair<Command, Int>

fun parseCommandFile(path: String): List<CommandLine> {
    val lines: List<String> = File(path).readLines()

    return lines.map {

        val splitLine: List<String> = it.split(" ")
        val command = when (splitLine[0]) {
            "forward" -> Command.Forward
            "up" -> Command.Up
            "down" -> Command.Down
            else -> throw Exception("Bad Command")
        }

        val arg = splitLine[1].toInt()

        CommandLine(command, arg)
    }
}


/* Move one step with the rules from part 1 of the problem */
typealias SubmarineState = Pair<Int, Int>
fun updatePos(state: SubmarineState, cmd: CommandLine): SubmarineState {
    val (command, arg) = cmd
    val (x, depth) = state
    return when (command) {
        Command.Forward -> SubmarineState(x + arg, depth)
        Command.Up -> SubmarineState(x, depth - arg)
        Command.Down -> SubmarineState(x, depth + arg)
    }
}

/* Move one step with the rules from part 2 of the problem */
typealias SubmarineStateWithDelta = Triple<Int, Int, Int>
fun updatePosWithDelta(state: SubmarineStateWithDelta, cmd: CommandLine): SubmarineStateWithDelta {
    val (command, arg) = cmd
    val (x, depth, delta) = state
    return when (command) {
        Command.Forward -> SubmarineStateWithDelta(x + arg, depth + delta * arg, delta)
        Command.Up -> SubmarineStateWithDelta(x, depth, delta - arg)
        Command.Down -> SubmarineStateWithDelta(x, depth, delta + arg)
    }
}


class Problem(override val inputFilePath: String) : DailyProblem {
    override val number = 2
    override val name = "Dive!"

    override fun part1(): Long {
        val commands = parseCommandFile(this.inputFilePath)
        val initialState = SubmarineState(0, 0)
        val (x, depth) = commands.fold(initialState) { pos, cmd -> updatePos(pos, cmd) }

        return (x * depth).toLong()
    }

    override fun part2(): Long {
        val commands = parseCommandFile(this.inputFilePath)
        val initialState = SubmarineStateWithDelta(0, 0, 0)
        val (x, depth, _) = commands.fold(initialState) { pos, cmd -> updatePosWithDelta(pos, cmd) }

        return (x * depth).toLong()
    }
}

val problem = Problem("input/day2.txt")