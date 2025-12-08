package AdventOfCodeKotlin.puzzles.AoC2020

import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner

typealias Color = String
typealias Rule = Set<String>

private const val SHINY_GOLD = "shiny gold"

class Day07 {

    companion object {
        fun part1(puzzle: PuzzleInputProvider) : String {
            val rules: Map<Color, Rule> = buildBagTree(puzzle.getAsString())
            val containers = findContainersDFS(rules)
            return containers.size.toString()
        }

        fun part2(puzzle: PuzzleInputProvider) : String {
            return ""
        }

        private fun buildBagTree(input: List<String>): Map<Color, Rule> {
            val rules = hashMapOf<Color, Rule>()
            input
                .forEach { line ->
                    val (parent, allChildren) = line
                        .replace(Regex("\\d+"), "")
                        .replace(Regex("bags?\\.?"), "")
                        .split("contain")
                        .map { it.trim() }
                    val childrenColors = allChildren.split(',').map { it.trim() }.toSet()
                    for (childColor in childrenColors) {
                        rules.compute(childColor) { _, current ->
                            if (current == null) setOf(parent)
                            else current + parent
                        }
                    }
                }
            return rules
        }

        fun findContainersDFS(rules: Map<Color, Rule>): Set<Color> {
            var known = setOf(SHINY_GOLD)
            var next = setOf(SHINY_GOLD) + rules[SHINY_GOLD]!!
            while (true) {
                val toFind = next - known
                if (toFind.isEmpty()) break
                known = known + next
                next = toFind.mapNotNull { rules[it] }.flatten().toSet()
            }
            return known - SHINY_GOLD
        }
    }
}


fun main() {

    Runner.solve(2020, 7, Day07::part1)
}