fun day1_1(): Int {
    val input = parseIntsFile("input/day1.txt")
    return countIncreases(input)
}

fun day1_2(): Int {
    val input = parseIntsFile("input/day1.txt")
    val windowedSums = input.windowed(3).map { it.sum() }
    return countIncreases(windowedSums)
}