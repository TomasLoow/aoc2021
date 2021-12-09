package day9

class SeaFloor(private val map: Array<Array<Int>>) {
    val height = this.map.size
    val width = this.map[0].size

    private fun isLowestPoint(p: Coordinate): Boolean {
        val here = valueAt(p)
        val neighbourValues = neighbourValuesOf(p)
        return neighbourValues.all { it > here }
    }

    private fun neighbourValuesOf(p: Coordinate): List<Int> {
        val (x, y) = p
        return listOf(
            Coordinate(x + 1, y), Coordinate(x - 1, y), Coordinate(x, y + 1), Coordinate(x, y - 1),
        )
            .filter {
                val (nx, ny) = it
                (nx >= 0) && (ny >= 0) && (nx < width) && (ny < height)
            }
            .map { valueAt(it) }
    }

    fun findLowestPoints(): List<Coordinate> {
        val allPoints = (0 until height).flatMap { y ->
            (0 until width).map { x ->
                Coordinate(x, y)
            }
        }
        return allPoints.filter { isLowestPoint(it) }
    }

    fun valueAt(p: Coordinate) = map[p.second][p.first]

    private fun floodFill(p: Coordinate, basinPoints: MutableSet<Coordinate>) {
        if (basinPoints.contains(p)) {
            return
        }
        val (x, y) = p
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return
        }
        if (valueAt(p) == 9) {
            return
        }

        basinPoints.add(p)
        floodFill(Coordinate(x + 1, y), basinPoints)
        floodFill(Coordinate(x - 1, y), basinPoints)
        floodFill(Coordinate(x, y + 1), basinPoints)
        floodFill(Coordinate(x, y - 1), basinPoints)
    }

    fun sizeOfBasinAt(p: Coordinate): Int {
        val basin = mutableSetOf<Coordinate>()
        floodFill(p, basin)
        return basin.size
    }


}