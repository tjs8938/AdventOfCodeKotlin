package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner


class Day02 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            return puzzle.getAsString().map { Game(it) }
                .filter { it.red() <= 12 && it.green() <= 13 && it.blue() <= 14 }
                .map { it.gameId }
                .sum().toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            return puzzle.getAsString().map { Game(it) }
                .map { it.red() * it.green() * it.blue() }
                .sum().toString()
        }
    }
}

class Game(input: String) {
    var gameId: Int = 0
    val colorCounts = mutableMapOf<String, Int>()
    init {
        val split = input.split(":")
        gameId = split[0].substring(5).toInt()
        val regex = Regex("""([0-9]+) (red|green|blue)""")
        regex.findAll(split[1]).forEach {
            it.destructured.let { (count, color) ->
                val bestYet = colorCounts.getOrDefault(color, 0)
                if (bestYet < count.toInt()) {
                    colorCounts[color] = count.toInt()
                }
            }
        }
    }

    fun red(): Int = colorCounts.get("red")!!
    fun green(): Int = colorCounts.get("green")!!
    fun blue(): Int = colorCounts.get("blue")!!

    override fun toString(): String {
        return "Game(gameId=$gameId, colorCounts=$colorCounts)"
    }
}


fun main() {
    val example = ExamplePuzzle("""Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""")
    assert(Day02.part1(example) == "8")
//    Runner.solve(2023, 2, part1 = Day02::part1)
    Runner.solve(2023, 2, part2 = Day02::part2)
}