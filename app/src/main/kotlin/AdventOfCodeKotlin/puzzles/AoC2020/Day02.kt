package AdventOfCodeKotlin.puzzles

import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner



data class PasswordWithPolicy(
    val password: String,
    val range: IntRange,
    val letter: Char
) {
    companion object {
        private val regex = Regex("""(\d+)-(\d+) ([a-z]): ([a-z]+)""")

        fun parse(line: String): PasswordWithPolicy =
            regex.matchEntire(line)!!
                .destructured
                .let { (start, end, letter, password) ->
                    PasswordWithPolicy(password, start.toInt()..end.toInt(), letter.single())
                }

    }
}

class Day02 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider) : String {
            val input = puzzle
            .getAsString()
            .map(PasswordWithPolicy::parse)

            return (input.count { policy -> policy.password.count { it == policy.letter } in policy.range }).toString()
        }

        fun part2(puzzle: PuzzleInputProvider) : String {
            val input = puzzle
            .getAsString()
            .map(PasswordWithPolicy::parse)

            return (input.count{ policy -> (policy.password[policy.range.first - 1] == policy.letter) xor (policy.password[policy.range.last - 1] == policy.letter) }).toString()
        }
    }
}


fun main() {
    Runner.solve(2020, 2, Day02::part1, Day02::part2)
}