package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import kotlin.math.floor
import kotlin.math.pow

class Day17 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): Any {
            val (registerInput, programInput) = puzzle.get().split("\n\n")
            val registers = registerInput.lines().map { it.removePrefix("Register ").split(": ") }
                .associate { it[0].first() to it[1].toInt() }.toMutableMap()

            val program = programInput.removePrefix("Program: ").split(",").map { it.toInt() }
            var pc = 0
            val outputs = mutableListOf<Int>()

            fun combo(op: Int): Int {
                return when (op) {
                    0 -> 0
                    1 -> 1
                    2 -> 2
                    3 -> 3
                    4 -> registers['A']!!
                    5 -> registers['B']!!
                    6 -> registers['C']!!
                    else -> throw IllegalArgumentException("Invalid op code")
                }
            }

            val instructions = listOf<(Int) -> Unit>(
                { op ->
                    registers['A'] = floor(registers.getOrDefault('A', 0) / 2.0.pow(combo(op).toDouble())).toInt()
                },
                { op -> registers['B'] = registers.getOrDefault('B', 0) xor op },
                { op -> registers['B'] = combo(op).mod(8) },
                { op -> if (registers.getOrDefault('A', 0) != 0) pc = op - 1 },
                { op -> registers['B'] = registers['B']!! xor registers['C']!! },
                { op -> outputs.add(combo(op).mod(8)) },
                { op ->
                    registers['B'] = floor(registers.getOrDefault('A', 0) / 2.0.pow(combo(op).toDouble())).toInt()
                },
                { op ->
                    registers['C'] = floor(registers.getOrDefault('A', 0) / 2.0.pow(combo(op).toDouble())).toInt()
                },
            )

            while (pc < program.size) {
                val inst = instructions[program[pc++]]
                inst(program[pc])
                pc++
            }

            return outputs.joinToString(",")
        }

        fun part2(puzzle: PuzzleInputProvider): Any {
            val (_, programInput) = puzzle.get().split("\n\n")

            val outputs = programInput.removePrefix("Program: ").split(",").map { it.toInt() }

            var candidates = listOf(0L)

            val (firstMask, secondMask) = outputs.windowed(2, 2).filter { it[0] == 1 }.map { it[1] }

            outputs.reversed().forEach { output ->
                candidates = candidates.flatMap { a ->
                    (0..7).mapNotNull { b ->
                        var newB = b xor firstMask
                        val newA = (a shl 3) + b
                        val c = (newA shr newB) and 7
                        newB = newB xor secondMask xor c.toInt()
                        if (newB == output) {
                            newA
                        } else {
                            null
                        }
                    }
                }
            }

            return candidates.first()
        }
    }
}

fun main() {
    val ex1 = ExamplePuzzle(
        """
        Register A: 729
        Register B: 0
        Register C: 0

        Program: 0,1,5,4,3,0
    """.trimIndent()
    )

    Day17.part1(ex1).let {
        println(it)
        if (it != "4,6,3,5,6,3,5,2,1,0") throw AssertionError("Test failed")
    }

    val ex2 = ExamplePuzzle(
        """
        Register A: 2024
        Register B: 0
        Register C: 0

        Program: 0,3,5,4,3,0
    """.trimIndent()
    )

//    Day17.part2(ex2).let {
//        println(it)
//        if (it != 117440) throw AssertionError("Test failed")
//    }
    Runner.solve(2024, 17, part1 = Day17::part1)
    Runner.solve(2024, 17, part2 = Day17::part2)
}