package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import AdventOfCodeKotlin.puzzles.AoC2022.Day23.Directions.*


class Day23 {
    companion object {
        private fun moveRound(
            elfMap: MutableMap<Pair<Int, Int>, Elf>,
            generalDirections: MutableList<Pair<List<Directions>, Directions>>
        ): Boolean {
            val proposals = mutableMapOf<Pair<Int, Int>, MutableList<Elf>>()
            var anyoneMoved = false
            elfMap.values.forEach { elf ->
                val proposal = elf.propose(elfMap.keys, generalDirections)
                proposal?.let {
                    if (proposal in proposals.keys) {
                        proposals[proposal]?.add(elf)
                    } else {
                        proposals[proposal] = mutableListOf(elf)
                    }
                }
            }

            // find proposals with exactly 1 elf, and move that elf there
            proposals.filter { it.value.size == 1 }.forEach { (dest, elves) ->
                val elf = elves.first()
                elfMap.remove(elf.location)
                elf.location = dest
                elfMap[dest] = elf
                anyoneMoved = true
            }

            // rotate the list of directions to check
            val d = generalDirections.removeFirst()
            generalDirections.add(d)

            return anyoneMoved
        }

        private fun parseInput(puzzle: PuzzleInputProvider): MutableMap<Pair<Int, Int>, Elf> {
            val elfMap: MutableMap<Pair<Int, Int>, Elf> = mutableMapOf()
            puzzle.getAsString().forEachIndexed { y, row ->
                row.forEachIndexed { x, c ->
                    if (c == '#') {
                        elfMap[x to y] = Elf(x to y)
                    }
                }
            }
            return elfMap
        }

        private fun getGeneralDirections(): MutableList<Pair<List<Directions>, Directions>> {
            return mutableListOf(
                listOf(NORTH, NORTH_EAST, NORTH_WEST) to NORTH,
                listOf(SOUTH, SOUTH_EAST, SOUTH_WEST) to SOUTH,
                listOf(WEST, NORTH_WEST, SOUTH_WEST) to WEST,
                listOf(EAST, SOUTH_EAST, NORTH_EAST) to EAST
            )
        }

        fun part1(puzzle: PuzzleInputProvider): String {

            val generalDirections = getGeneralDirections()

            val elfMap: MutableMap<Pair<Int, Int>, Elf> = parseInput(puzzle)

            repeat(10) {
                // Find where all of the elves want to move
                moveRound(elfMap, generalDirections)
            }

            // Find the bounding area containing all elves
            val minX = elfMap.keys.minOf { it.first }
            val maxX = elfMap.keys.maxOf { it.first }
            val minY = elfMap.keys.minOf { it.second }
            val maxY = elfMap.keys.maxOf { it.second }

            val area = (maxX - minX + 1) * (maxY - minY + 1)
            return (area - elfMap.size).toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val generalDirections = getGeneralDirections()

            val elfMap: MutableMap<Pair<Int, Int>, Elf> = parseInput(puzzle)

            var roundCount = 0
            while(moveRound(elfMap, generalDirections)) {
                roundCount++
            }

            return (roundCount + 1).toString()
        }
    }

    enum class Directions(private val offset: Pair<Int, Int>) {
        NORTH(0 to -1),
        NORTH_EAST(1 to -1),
        EAST(1 to 0),
        SOUTH_EAST(1 to 1),
        SOUTH(0 to 1),
        SOUTH_WEST(-1 to 1),
        WEST(-1 to 0),
        NORTH_WEST(-1 to -1);

        operator fun plus(location: Pair<Int, Int>): Pair<Int, Int> {
            return (location.first + offset.first) to (location.second + offset.second)
        }
    }

    data class Elf(var location: Pair<Int, Int>) {

        fun propose(
            otherLocations: Collection<Pair<Int, Int>>,
            generalDirections: MutableList<Pair<List<Directions>, Directions>>
        ): Pair<Int, Int>? {

            var moveLocation: Pair<Int, Int>? = null
            // check for any neighbors
            if (Directions.values().any { d -> otherLocations.contains(d + location) }) {
                generalDirections.forEach { (directionsToCheck, destination) ->
                    if (moveLocation == null && directionsToCheck.none { d ->
                            otherLocations.contains(d + location)
                        }) {
                        moveLocation = destination + location
                    }
                }
            }

            return moveLocation
        }
    }
}


fun main() {
//    Runner.solve(2022, 23, part1 = Day23::part1)

    val example = ExamplePuzzle("..............\n" +
        "..............\n" +
        ".......#......\n" +
        ".....###.#....\n" +
        "...#...#.#....\n" +
        "....#...##....\n" +
        "...#.###......\n" +
        "...##.#.##....\n" +
        "....#..#......\n" +
        "..............\n" +
        "..............\n" +
        "..............")

    assert(Day23.part2(example) == "20")

    Runner.solve(2022, 23, part2 = Day23::part2)
}

