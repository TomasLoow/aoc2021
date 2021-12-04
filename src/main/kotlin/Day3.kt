enum class CountMode {
    MostCommon,
    LeastCommon,
}

fun findMostCommonByIndex(rows: List<Array<Boolean>>, idx: Int, mode: CountMode) : Boolean {
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

fun binaryToInt(bits: Array<Boolean>): Int {
    var res = 0
    for (bit in bits) {
        res *= 2
        if (bit) res++
    }
    return res
}

fun day3_1(): Int {
    val input = parseBinaryFile("input/day3.txt")

    val rowLength: Int = input[0].size

    val delta: Array<Boolean> = Array<Boolean>(rowLength) { false }
    val epsilon: Array<Boolean> = Array<Boolean>(rowLength) { false }

    for (idx in 0.rangeTo(rowLength-1)) {
        val mostCommon = findMostCommonByIndex(input, idx, CountMode.MostCommon)
        delta[idx] = mostCommon
        epsilon[idx] = !mostCommon
    }

    val deltaVal = binaryToInt(delta)
    val epsilonVal = binaryToInt(epsilon)

    return deltaVal * epsilonVal
}


fun day3_2(): Int {
    val oxygen = bitCriteria("input/day3.txt", CountMode.MostCommon)
    val co2 = bitCriteria("input/day3.txt", CountMode.LeastCommon)
    return oxygen*co2
}

private fun bitCriteria(path: String, mode: CountMode):Int {
    var rows: List<Array<Boolean>> = parseBinaryFile(path)

    val rowLength: Int = rows[0].size

    for (idx in 0.rangeTo(rowLength - 1)) {
        val target = findMostCommonByIndex(rows, idx, mode)
        rows = rows.filter { it[idx] == target }
        if (rows.size == 1) break
    }
    return binaryToInt(rows[0])
}

