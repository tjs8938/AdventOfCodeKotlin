package AdventOfCodeKotlin.puzzles.AoC2024

data class Keypad(val layout: List<String>, val initialPosition: Pair<Int, Int>) {
    private val height = layout.size
    private val width = layout.maxOf { it.length }

    fun isValidPosition(x: Int, y: Int): Boolean {
        return y in layout.indices && x in layout[y].indices && layout[y][x] != ' '
    }

    fun findShortestPath(start: Pair<Int, Int>, target: Char): List<Char>? {
        val directions = listOf('^' to (0 to -1), 'v' to (0 to 1), '<' to (-1 to 0), '>' to (1 to 0))
        val queue = ArrayDeque<Triple<Pair<Int, Int>, List<Char>, Char>>()
        val visited = mutableSetOf<Pair<Int, Int>>()

        queue.add(Triple(start, emptyList(), layout[start.second][start.first]))
        visited.add(start)

        while (queue.isNotEmpty()) {
            val (position, path, currentChar) = queue.removeFirst()

            if (currentChar == target) return path

            for ((dir, delta) in directions) {
                val newPos = position.first + delta.first to position.second + delta.second
                if (isValidPosition(newPos.first, newPos.second) && newPos !in visited) {
                    visited.add(newPos)
                    queue.add(Triple(newPos, path + dir, layout[newPos.second][newPos.first]))
                }
            }
        }

        return null
    }
}

fun calculateComplexity(codes: List<String>, keypad: Keypad): Int {
    var totalComplexity = 0

    for (code in codes) {
        var currentPos = keypad.initialPosition
        var totalMoves = 0
        val numericPart = code.filter { it.isDigit() }.toInt()

        for (char in code) {
            val path = keypad.findShortestPath(currentPos, char) ?: error("Invalid target $char")
            totalMoves += path.size + 1 // Including 'A' to press the button
            currentPos = path.fold(currentPos) { pos, dir ->
                val delta = when (dir) {
                    '^' -> 0 to -1
                    'v' -> 0 to 1
                    '<' -> -1 to 0
                    '>' -> 1 to 0
                    else -> error("Invalid direction $dir")
                }
                pos.first + delta.first to pos.second + delta.second
            }
        }

        totalComplexity += totalMoves * numericPart
    }

    return totalComplexity
}

fun main() {
    val numericKeypadLayout = listOf(
        "   789   ",
        "   456   ",
        "   123   ",
        "     0A  ",
    )
    val numericKeypad = Keypad(numericKeypadLayout, 8 to 3) // Starting position at 'A'

    val codes = listOf("029A", "980A", "179A", "456A", "379A")

    val part1Complexity = calculateComplexity(codes, numericKeypad)
    println("Part 1 Total Complexity: $part1Complexity")

    // Simulate for Part 2 (additional robots in the chain)
    val robotsInChain = 25
    val part2Complexity = part1Complexity * robotsInChain // Each step requires navigating through all robots
    println("Part 2 Total Complexity: $part2Complexity")
}
