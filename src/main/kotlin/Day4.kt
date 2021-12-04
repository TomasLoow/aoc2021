import java.io.File

fun parseBingoFile(path: String): Pair<List<Int>, List<BingoBoard>> {
    var lines: List<String> = File(path).readLines()
    val balls = lines[0].split(",").map { it.toInt() }
    lines = lines.drop(2)

    val boards = ArrayList<BingoBoard>(0)
    while (lines.size >= 5) {
        val board = parseBoard(lines.subList(0,5))
        boards.add(board)
        lines = lines.drop(6)  // Five for board, one separator
    }
    return Pair(balls, boards)
}

private fun parseBoard(rows: List<String>) : BingoBoard {
    val allNums: List<Int> = rows.flatMap { it.trim().split(" +".toRegex()).map{ it.toInt() } }
    return BingoBoard(allNums.toTypedArray())
}

fun day4_1():Int {
    val data : Pair<List<Int>, List<BingoBoard>> = parseBingoFile("input/day4.txt")

    val balls = data.first
    val boards = data.second


    for (ball in balls) {
        for (board in boards) {
            board.markNumber(ball)
            if (board.checkBingo()) {
                val score = board.getScore()
                return ball*score
            }
        }
    }
    throw Exception("No bingo")
}


fun day4_2():Int {
    val data : Pair<List<Int>, List<BingoBoard>> = parseBingoFile("input/day4.txt")

    val balls = data.first
    val boards = data.second

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
                    return ball*score
                }
            }
        }
    }
    throw Exception("No bingo")
}
