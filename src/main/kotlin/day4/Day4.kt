package day4

import DailyProblem
import java.io.File

fun parseBingoFile(path: String): Pair<List<Int>, List<BingoBoard>> {
    var lines: List<String> = File(path).readLines()
    val balls = lines[0]
        .split(",")
        .map { it.toInt() }
    lines = lines.drop(2)

    val boards = ArrayList<BingoBoard>(0)
    while (lines.size >= 5) {
        val board = BingoBoard(lines.subList(0, 5))
        boards.add(board)
        lines = lines.drop(6)  // Five for board, one separator
    }
    return Pair(balls, boards)
}

class Problem(override val inputFilePath: String) : DailyProblem {
    override val number = 4
    override fun part1(): Long {
        val data: Pair<List<Int>, List<BingoBoard>> = parseBingoFile(this.inputFilePath)

        val (balls, boards) = data

        for (ball in balls) {
            for (board in boards) {
                board.markNumber(ball)
                if (board.checkBingo()) {
                    val score = board.getScore()
                    return (ball * score).toLong()
                }
            }
        }
        throw Exception("No bingo")
    }

    override fun part2(): Long {
        val data: Pair<List<Int>, List<BingoBoard>> = parseBingoFile(this.inputFilePath)

        val (balls, boards) = data

        val numberOfBoards = boards.size
        var numberOfWins = 0

        for (ball in balls) {
            for (board in boards) {
                if (board.hasWon) continue
                board.markNumber(ball)
                if (board.checkBingo()) {
                    board.hasWon = true
                    numberOfWins++
                    if (numberOfWins == numberOfBoards) {
                        val score = board.getScore()
                        return (ball * score).toLong()
                    }
                }
            }
        }
        throw Exception("No bingo")
    }
}

val problem = Problem("input/day4.txt")
