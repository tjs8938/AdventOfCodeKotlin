package AdventOfCodeKotlin.framework

import framework.User
import java.time.Instant

class Runner {
    companion object {
        fun solve(year: Int, day: Int, part1: ((PuzzleInputProvider) -> String)? = null , part2: ((PuzzleInputProvider) -> String)? = null) {
            println("$year - Day $day")
            User.allUsers().forEach { user: User ->
                val puzzle = Puzzle(year, day, user)
                println("User ${user.email}:")
                part1?.let {
                    val startTime = Instant.now()
                    val part1Answer = it.invoke(puzzle)
                    println("Part 1 - $part1Answer - calculated in ${Instant.now().toEpochMilli() - startTime.toEpochMilli()} ms")
                    puzzle.post(part1Answer, 1)
                }
                part2?.let {
                    val startTime = Instant.now()
                    val part2Answer = it.invoke(puzzle)
                    println("Part 2 - $part2Answer - calculated in ${Instant.now().toEpochMilli() - startTime.toEpochMilli()} ms")
                    puzzle.post(part2Answer, 2)
                }
            }
        }
    }
}