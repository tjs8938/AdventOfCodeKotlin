package AdventOfCodeKotlin.framework

import framework.User
import java.time.Instant

class Runner {
    companion object {
        fun solve(
            year: Int,
            day: Int,
            part1: ((PuzzleInputProvider) -> Any)? = null,
            part2: ((PuzzleInputProvider) -> Any)? = null
        ) {
            part1?.let {
                solve(year, day, 1, it)
            }
            part2?.let {
                solve(year, day, 2, it)
            }
        }

        fun solve(year: Int, day: Int, part: Int, solver: ((PuzzleInputProvider) -> Any), vararg examples: ExamplePuzzle) {
            println("$year - Day $day")
            for (example in examples) {
                println("Example:")
                val answer = solver.invoke(example)
                example.post(answer.toString(), part)
            }
            User.allUsers().forEach { user: User ->
                val puzzle = Puzzle(year, day, user)
                println("User ${user.email}:")
                solver.let {
                    val startTime = Instant.now()
                    val answer = it.invoke(puzzle)
                    println(
                        "Part $part - $answer - calculated in ${
                            Instant.now().toEpochMilli() - startTime.toEpochMilli()
                        } ms"
                    )
                    puzzle.post(answer.toString(), part)
                }
            }
        }
    }
}