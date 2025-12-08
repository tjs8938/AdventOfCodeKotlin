package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import java.time.Instant


class Day21 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            return countLocations(puzzle, 64).toString()
        }

        fun countLocations(puzzle: PuzzleInputProvider, steps: Int): Int {
            val garden = Garden(puzzle.getAsString())

            return countLocations(garden, steps, garden.startLocation)
        }

        fun countLocations(garden: Garden, steps: Int, start: Pair<Int, Int>? = null): Int {
            if (steps <= 0) {
                return 0
            }

            val s = start ?: garden.startLocation
            var locationsToProcess = mutableSetOf(s)
            val locationsSeen = mutableSetOf<Pair<Int, Int>>()
            var validLocations = 0

            (1..steps).forEach { step ->
                val newLocationsToProcess = mutableSetOf<Pair<Int, Int>>()
                while (locationsToProcess.isNotEmpty()) {
                    val loc = locationsToProcess.pop()
                    Day10.Directions.values().forEach { dir ->
                        val next = dir.plus(loc)
                        if (!locationsSeen.contains(next) &&
                            garden.contains(next) && (garden.get(next) == '.' || garden.get(next) == 'S')
                        ) {
                            newLocationsToProcess.add(next)
                            locationsSeen.add(next)
                        }
                    }
                }

                if (steps % 2 == step % 2) {
                    validLocations += newLocationsToProcess.size
                }
                locationsToProcess = newLocationsToProcess
            }
            return validLocations
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            return countLocations2(puzzle, 26501365).toString()
        }

        fun countLocations2(puzzle: PuzzleInputProvider, steps: Int): Long {
            val garden = Garden(puzzle.getAsString())
            val blockSize = garden.size
            val gridSize = steps / blockSize - 1

            val oddCount = countLocations(garden, (blockSize * 2) + 1)
            val evenCount = countLocations(garden, (blockSize * 2))

            val oddBlocks: Long = (gridSize / 2 * 2 + 1L).let { it * it }
            val evenBlocks: Long = (gridSize.inc() / 2 * 2L).let { it * it }

            // This is the count of all valid spots if only counting the spots in which the entire "block" is reachable
            var validLocations = (evenBlocks * evenCount) + (oddBlocks * oddCount)


            // check the "little triangles" in each direction (e.g. the block at the very south end)
            val middleOfEdge = blockSize / 2
            val edges = listOf(0 to middleOfEdge, blockSize - 1 to middleOfEdge, middleOfEdge to 0, middleOfEdge to blockSize - 1)
            validLocations += edges.sumOf {
                countLocations(garden, blockSize - 1, it).toLong()
            }

            // check the grids with a corner "cut off"
            val corners = listOf(0 to 0, 0 to blockSize - 1, blockSize - 1 to 0, blockSize - 1 to blockSize - 1)
            validLocations += corners.sumOf {
                countLocations(garden, blockSize * 3 / 2 - 1, it).toLong()
            }.times(gridSize)

            // check the smaller triangles in between the cut off grids
            validLocations += corners.sumOf {
                countLocations(garden, blockSize / 2 - 1, it).toLong()
            }.times(gridSize + 1)

            return validLocations
        }
    }

    class Garden(x: List<String>) : ArrayList<String>(x) {
        val startLocation: Pair<Int, Int>

        init {
            var start: Pair<Int, Int>? = null
            this.forEachIndexed { index, s ->
                val i = s.indexOf('S')
                if (i >= 0) {
                    start = index to i
                }
            }
            startLocation = start!!
        }
    }
}


fun main() {

    val ex1 = ExamplePuzzle(
        """
        ...........
        .....###.#.
        .###.##..#.
        ..#.#...#..
        ....#.#....
        .##..S####.
        .##..#...#.
        .......##..
        .##.#.####.
        .##..##.##.
        ...........
    """.trimIndent()
    )

//    println(Day21.countLocations(ex1, 1000))

//    Day21.countLocations(ex1, 6).let {
////        println(it)
//        assert(it == 16)
//    }

    Runner.solve(2023, 21, part1 = Day21::part1)

    val ex2 = ExamplePuzzle(
        """
        .......    
        .......
        .......
        ...S...
        .......
        .......
        .......
    """.trimIndent()
    )


    listOf(
        17 to 324
    )
        .forEach {
            val startTime = Instant.now()
            Day21.countLocations2(ex2, it.first).let { ans ->
                println(ans)
                assert(ans == it.second.toLong())
            }
            println("calculated in ${Instant.now().toEpochMilli() - startTime.toEpochMilli()} ms")
        }
    Runner.solve(2023, 21, part2 = Day21::part2)
}