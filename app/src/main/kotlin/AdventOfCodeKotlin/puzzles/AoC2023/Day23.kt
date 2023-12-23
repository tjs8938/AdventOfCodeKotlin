package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner


class Day23 {
    companion object {

        fun part1(puzzle: PuzzleInputProvider): String {

            val island = mutableMapOf<Pair<Int, Int>, Tile>()
            val input = puzzle.getAsString()
            input.forEachIndexed { rowIndex, s ->
                s.forEachIndexed { columnIndex, c ->
                    if (listOf('.', '^', '<', '>', 'v').contains(c)) {
                        val loc = rowIndex to columnIndex
                        island[loc] = Tile(loc)
                        val aboveLoc = rowIndex - 1 to columnIndex
                        if (island.containsKey(aboveLoc)) {
                            // link the tile above
                            when (c to input.get(aboveLoc)) {
                                '.' to '.' -> {
                                    island[loc]!!.neighbors[island[aboveLoc]!!] = 1
                                    island[aboveLoc]!!.neighbors[island[loc]!!] = 1
                                }

                                '.' to '^' -> island[loc]!!.neighbors[island[aboveLoc]!!] = 1
                                '^' to '.' -> island[loc]!!.neighbors[island[aboveLoc]!!] = 1
                                'v' to '.' -> island[aboveLoc]!!.neighbors[island[loc]!!] = 1
                                '.' to 'v' -> island[aboveLoc]!!.neighbors[island[loc]!!] = 1
                            }
                        }

                        val leftLoc = rowIndex to columnIndex - 1
                        if (island.containsKey(leftLoc)) {
                            // link the tile to the left
                            when (input.get(leftLoc) to c) {
                                '.' to '.' -> {
                                    island[loc]!!.neighbors[island[leftLoc]!!] = 1
                                    island[leftLoc]!!.neighbors[island[loc]!!] = 1
                                }

                                '.' to '>' -> island[leftLoc]!!.neighbors[island[loc]!!] = 1
                                '>' to '.' -> island[leftLoc]!!.neighbors[island[loc]!!] = 1
                                '<' to '.' -> island[loc]!!.neighbors[island[leftLoc]!!] = 1
                                '.' to '<' -> island[loc]!!.neighbors[island[leftLoc]!!] = 1
                            }
                        }
                    }
                }
            }

            return island[0 to 1]!!.longestPath(null).toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val island = mutableMapOf<Pair<Int, Int>, Tile>()
            val input = puzzle.getAsString()

            input.forEachIndexed { rowIndex, s ->
                s.forEachIndexed { columnIndex, c ->
                    if (listOf('.', '^', '<', '>', 'v').contains(c)) {
                        val loc = rowIndex to columnIndex
                        island[loc] = Tile(loc)
                        val aboveLoc = rowIndex - 1 to columnIndex
                        if (island.containsKey(aboveLoc)) {
                            // link the tile above
                            island[loc]!!.neighbors[island[aboveLoc]!!] = 1
                            island[aboveLoc]!!.neighbors[island[loc]!!] = 1
                        }

                        val leftLoc = rowIndex to columnIndex - 1
                        if (island.containsKey(leftLoc)) {
                            // link the tile to the left
                            island[loc]!!.neighbors[island[leftLoc]!!] = 1
                            island[leftLoc]!!.neighbors[island[loc]!!] = 1
                        }
                    }
                }
            }

            // collapse the graph so that the only tiles left are the start and end tiles, as well as any junctions
            island.values.filter { it.neighbors.size == 2 }.forEach { straight ->
                // any tile with exactly 2 neighbors can be collapsed. Connect the 2 neighbors to each other with a combined distance
                val (tile1, dist1) = straight.neighbors.entries.first()
                val (tile2, dist2) = straight.neighbors.entries.last()

                tile1.neighbors.remove(straight)
                tile1.neighbors[tile2] = dist1 + dist2

                tile2.neighbors.remove(straight)
                tile2.neighbors[tile1] = dist1 + dist2

                island.remove(straight.location)
            }

            island.values.first { it.location.first == input.size - 1}.isGoal = true

            val visited = mutableSetOf<Tile>()
            return island[0 to 1]!!.longestPath(visited).toString()
        }

        class Tile(val location: Pair<Int, Int>, var isGoal: Boolean = false) {
            val neighbors = mutableMapOf<Tile, Int>()

            fun longestPath(previous: Tile?): Int {
                return if (neighbors.filterNot { it.key == previous }.isEmpty()) {
                    0
                } else {
                    neighbors.filterNot { it.key == previous }.maxOf { it.key.longestPath(this) + it.value }
                }
            }

            fun longestPath(tilesVisited: MutableSet<Tile>): Int {
                return if (isGoal) {
                    0
                } else {
                    tilesVisited.add(this)
                    val unvisitedNeighbors = neighbors.keys.filterNot { tilesVisited.contains(it) }
                    var maxDist = Int.MIN_VALUE
                    unvisitedNeighbors.forEach { n ->
                        maxDist = maxOf(maxDist, n.longestPath(tilesVisited) + this.neighbors[n]!!)
                    }
                    tilesVisited.remove(this)
                    maxDist
                }
            }

            override fun toString(): String {
                return "Tile(location=$location, neighbors=${neighbors.map { it.key.location }})"
            }
        }
    }
}


fun main() {

    val ex1 = ExamplePuzzle(
        """
        #.#####################
        #.......#########...###
        #######.#########.#.###
        ###.....#.>.>.###.#.###
        ###v#####.#v#.###.#.###
        ###.>...#.#.#.....#...#
        ###v###.#.#.#########.#
        ###...#.#.#.......#...#
        #####.#.#.#######.#.###
        #.....#.#.#.......#...#
        #.#####.#.#.#########v#
        #.#...#...#...###...>.#
        #.#.#v#######v###.###v#
        #...#.>.#...>.>.#.###.#
        #####v#.#.###v#.#.###.#
        #.....#...#...#.#.#...#
        #.#########.###.#.#.###
        #...###...#...#...#.###
        ###.###.#.###v#####v###
        #...#...#.#.>.>.#.>.###
        #.###.###.#.###.#.#v###
        #.....###...###...#...#
        #####################.#
    """.trimIndent()
    )

//    Day23.part1(ex1).let {
//        println(it)
//        assert(it == "94")
//    }
//    Runner.solve(2023, 23, part1 = Day23::part1)

    Day23.part2(ex1).let {
        println(it)
        assert(it == "154")
    }
    Runner.solve(2023, 23, part2 = Day23::part2)
}