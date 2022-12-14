package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner


class Day12 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {

            val heightMap = puzzle.getAsString().map { it.toList() }
            val start: Pair<Int, Int> =
                heightMap.mapIndexed { y, row -> row.indexOf('S') to y }.first { it.first != -1 }
            val seenLocations = mutableSetOf(start)
            val queue = ArrayDeque(listOf(Triple(start.first, start.second, 0)))

            return searchBestPath(queue, heightMap, seenLocations)
        }

        private fun findMoves(
            start: Pair<Int, Int>,
            heightMap: Collection<Collection<Any>>
        ): List<Pair<Int, Int>> {
            return listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)
                .map { (it.first + start.first) to (it.second + start.second) }
                .filter { it.first in (heightMap.first().indices) && it.second in (heightMap.indices) }
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val heightMap = puzzle.getAsString().map { it.toList() }
            val seenLocations = mutableSetOf<Pair<Int, Int>>()
            val queue = ArrayDeque<Triple<Int, Int, Int>>()

            heightMap.forEachIndexed { y, chars ->
                chars.forEachIndexed { x, c ->
                    if (c == 'S' || c == 'a') {
                        seenLocations.add(x to y)
                        queue.add(Triple(x, y, 0))
                    }
                }
            }

            return searchBestPath(queue, heightMap, seenLocations)
        }

        private fun searchBestPath(
            queue: ArrayDeque<Triple<Int, Int, Int>>,
            heightMap: List<List<Char>>,
            seenLocations: MutableSet<Pair<Int, Int>>
        ): String {
            while (queue.size > 0) {
                val current = queue.removeFirst()
                val currentHeight = if (heightMap[current.second][current.first] == 'S') {
                    'a'
                } else {
                    heightMap[current.second][current.first]
                }
                findMoves(
                    current.first to current.second,
                    heightMap
                ) // find adjacent spaces within the range of the map
                    .filter { !seenLocations.contains(it) } // filter out locations that have been visited
                    .forEach {
                        if (heightMap[it.second][it.first] == 'E' && currentHeight >= 'y') {
                            // Found the destination
                            return (current.third + 1).toString()
                        } else if (heightMap[it.second][it.first] != 'E' && heightMap[it.second][it.first] - 1 <= currentHeight) {
                            seenLocations.add(it)
                            queue.add(Triple(it.first, it.second, current.third + 1))
                        }
                    }

            }
            return ""
        }
    }
}


fun main() {
//    Runner.solve(2022, 12, part1 = Day12::part1)
    Runner.solve(2022, 12, part2 = Day12::part2)
}

