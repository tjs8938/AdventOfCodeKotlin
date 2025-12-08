package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import java.security.InvalidParameterException


class Day02 {
    companion object {

        val part1Mapping = listOf("B X", "C Y", "A Z", "A X", "B Y", "C Z", "C X", "A Y", "B Z")

        fun betterPart1(puzzle: PuzzleInputProvider) : String {
            return puzzle.getAsString()
                .map { part1Mapping.indexOf(it) + 1 }
                .sum().toString()
        }


        val part2Mapping = listOf("B X", "C X", "A X", "A Y", "B Y", "C Y", "C Z", "A Z", "B Z")

        fun betterPart2(puzzle: PuzzleInputProvider) : String {
            return puzzle.getAsString()
                .map { part2Mapping.indexOf(it) + 1 }
                .sum().toString()
        }


        fun part1(puzzle: PuzzleInputProvider): String {
            return puzzle.getAsString().map { it.split(' ') }
                .map {
                    var shapeScore: Int = 0
                    var outcomeScore: Int = 0
                    when(it[1]) {
                        "X" -> {
                            shapeScore = 1
                            outcomeScore = when(it[0]) {
                                "A" -> 3
                                "B" -> 0
                                "C" -> 6
                                else -> throw InvalidParameterException("Invalid shape from opponent: ${it[1]}")
                            }
                        }
                        "Y" -> {
                            shapeScore = 2
                            outcomeScore = when(it[0]) {
                                "A" -> 6
                                "B" -> 3
                                "C" -> 0
                                else -> throw InvalidParameterException("Invalid shape from opponent: ${it[1]}")
                            }
                        }
                        "Z" -> {
                            shapeScore = 3
                            outcomeScore = when(it[0]) {
                                "A" -> 0
                                "B" -> 6
                                "C" -> 3
                                else -> throw InvalidParameterException("Invalid shape from opponent: ${it[1]}")
                            }
                        }
                    }
                    shapeScore + outcomeScore
                }
                .sum().toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            return puzzle.getAsString().map { it.split(' ') }
                .map {
                    var shapeScore: Int = 0
                    var outcomeScore: Int = 0
                    when(it[1]) {
                        "X" -> {
                            outcomeScore = 0
                            shapeScore = when(it[0]) {
                                "A" -> 3
                                "B" -> 1
                                "C" -> 2
                                else -> throw InvalidParameterException("Invalid shape from opponent: ${it[1]}")
                            }
                        }
                        "Y" -> {
                            outcomeScore = 3
                            shapeScore = when(it[0]) {
                                "A" -> 1
                                "B" -> 2
                                "C" -> 3
                                else -> throw InvalidParameterException("Invalid shape from opponent: ${it[1]}")
                            }
                        }
                        "Z" -> {
                            outcomeScore = 6
                            shapeScore = when(it[0]) {
                                "A" -> 2
                                "B" -> 3
                                "C" -> 1
                                else -> throw InvalidParameterException("Invalid shape from opponent: ${it[1]}")
                            }
                        }
                    }
                    shapeScore + outcomeScore
                }
                .sum().toString()
        }
    }
}


fun main() {
//    Runner.solve(2022, 2, part1 = Day02::part1)
    Runner.solve(2022, 2, part1 = Day02::betterPart1)
//    Runner.solve(2022, 2, part2 = Day02::part2)
    Runner.solve(2022, 2, part2 = Day02::betterPart2)
}