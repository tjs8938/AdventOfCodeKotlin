package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner


class Day08 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            val trees = puzzle.getAsString().map { it.toList().map { it.digitToInt() } }
            val visible: MutableSet<Pair<Int, Int>> = mutableSetOf()

            // look from the west
            trees.indices.forEach { treeRow ->
                var tallest = -1
                trees[0].indices.forEach { treeColumn ->
                    if (trees[treeRow][treeColumn] > tallest) {
                        visible.add(treeRow to treeColumn)
                        tallest = trees[treeRow][treeColumn]
                    }
                }
            }

            // look from the east
            trees.indices.forEach { treeRow ->
                var tallest = -1
                trees[0].indices.reversed().forEach { treeColumn ->
                    if (trees[treeRow][treeColumn] > tallest) {
                        visible.add(treeRow to treeColumn)
                        tallest = trees[treeRow][treeColumn]
                    }
                }
            }

            // look from the north
            trees[0].indices.forEach { treeColumn ->
                var tallest = -1
                trees.indices.forEach { treeRow ->
                    if (trees[treeRow][treeColumn] > tallest) {
                        visible.add(treeRow to treeColumn)
                        tallest = trees[treeRow][treeColumn]
                    }
                }
            }

            // look from the south
            trees[0].indices.forEach { treeColumn ->
                var tallest = -1
                trees.indices.reversed().forEach { treeRow ->
                    if (trees[treeRow][treeColumn] > tallest) {
                        visible.add(treeRow to treeColumn)
                        tallest = trees[treeRow][treeColumn]
                    }
                }
            }


            return visible.size.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val trees = puzzle.getAsString().map { it.toList().map { it.digitToInt() } }


            return trees.mapIndexed { treeHouseRow, row -> treeHouseRow to row }
                .flatMap { rowPair: Pair<Int, List<Int>> ->
                    rowPair.second.mapIndexed { treeHouseColumn, treeHouseHeight ->
                        Triple(
                            rowPair.first,
                            treeHouseColumn,
                            treeHouseHeight
                        )
                    }
                }
                .map { (treeHouseRow, treeHouseColumn, treeHouseHeight) ->
                    if (treeHouseRow == 0 || treeHouseRow == trees.size - 1 || treeHouseColumn == 0 || treeHouseColumn == trees[0].size - 1) {
                        0
                    } else {
                        var visibilityScore = 1

                        // look east
                        var visible = 0
                        for (it in (0 until treeHouseColumn).reversed()) {
                            if (trees[treeHouseRow][it] <= treeHouseHeight) {
                                visible++
                                if (trees[treeHouseRow][it] == treeHouseHeight) {
                                    break
                                }
                            }
                        }
                        visibilityScore *= visible
                        visible = 0

                        // look west
                        for (it in (treeHouseColumn + 1 until trees[0].size)) {
                            if (trees[treeHouseRow][it] <= treeHouseHeight) {
                                visible++
                                if (trees[treeHouseRow][it] == treeHouseHeight) {
                                    break
                                }
                            }
                        }
                        visibilityScore *= visible
                        visible = 0

                        // look north
                        for(it in (0 until treeHouseRow).reversed()) {
                            if (trees[it][treeHouseColumn] <= treeHouseHeight) {
                                visible++
                                if (trees[it][treeHouseColumn] == treeHouseHeight) {
                                    break
                                }
                            }
                        }
                        visibilityScore *= visible
                        visible = 0

                        // look south
                        for(it in (treeHouseRow + 1 until trees.size)) {
                            if (trees[it][treeHouseColumn] <= treeHouseHeight) {
                                visible++
                                if (trees[it][treeHouseColumn] == treeHouseHeight) {
                                    break
                                }
                            }
                        }
                        visibilityScore *= visible
                        visibilityScore
                    }
                }
                .max().toString()
        }
    }
}


fun main() {
    Runner.solve(2022, 8, part1 = Day08::part1)
    Runner.solve(2022, 8, part2 = Day08::part2)
}

