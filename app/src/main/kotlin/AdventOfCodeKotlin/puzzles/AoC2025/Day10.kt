package AdventOfCodeKotlin.puzzles.AoC2025

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import org.jetbrains.kotlinx.multik.api.linalg.LinAlg
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.api.zeros
import org.jetbrains.kotlinx.multik.default.linalg.DefaultLinAlgEx
import org.jetbrains.kotlinx.multik.ndarray.data.D2
import org.jetbrains.kotlinx.multik.ndarray.data.set
import kotlin.math.max

class Day10 {
    companion object {


        fun part1(puzzle: PuzzleInputProvider): String {
            class Machine(val target: Int, val buttons: List<Int>)

            val machineStrings = puzzle.getAsString()
            val machines = machineStrings.map { machine ->
                val targetString = machine.substring(1, machine.indexOfFirst { it == ']' })
                val target =
                    targetString.mapIndexed { i, c -> (if (c == '#') 1 else 0) shl i }.fold(0) { acc, v -> acc xor v }
                val buttons =
                    machine.substring(machine.indexOfFirst { it == '(' }, machine.indexOfLast { it == ')' } + 1)
                        .split(" ")
                        .map { it.trim().drop(1).dropLast(1).split(",").map { 1 shl it.trim().toInt() } }
                        .map { it.fold(0) { acc, v -> acc xor v } }
                Machine(target, buttons)
            }

            return machines.sumOf { machine ->
                var buttonCount = 0
                var lightStates = setOf(0)
                while (machine.target !in lightStates) {
                    lightStates = lightStates.flatMap { oldState ->
                        machine.buttons.map { oldState xor it }
                    }.toSet()
                    buttonCount++
                }
                buttonCount
            }.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            class Machine(val buttons: List<List<Int>>, val joltage: List<Int>)

            val machineStrings = puzzle.getAsString()
            val machines = machineStrings.map { machine ->
                val targetString = machine.substring(1, machine.indexOfFirst { it == ']' })
                val buttons =
                    machine.substring(machine.indexOfFirst { it == '(' }, machine.indexOfLast { it == ')' } + 1)
                        .split(" ")
                        .map { it.trim().drop(1).dropLast(1).split(",").map { it.trim().toInt() } }
                val joltage = machine.substring(machine.indexOfFirst { it == '{' })
                    .drop(1).dropLast(1).split(",").map { it.trim().toInt() }
                val dim = max(joltage.size, buttons.size)
                val mArray = mk.zeros<Double>(dim, dim)
                buttons.forEachIndexed { i, button ->
                    button.forEach { wire ->
                        mArray[wire, i] = 1.0
                    }
                }
                val joltageArray = mk.zeros<Double>(dim, 1)
                joltage.forEachIndexed { i, jolt ->
                    joltageArray[i, 0] = jolt.toDouble()
                }

                println(mArray)
                val result = DefaultLinAlgEx.solve(mArray, joltageArray)
                Machine(buttons, joltage)
            }



            return machines.sumOf { machine ->
                var buttonCount = 0
                var lightStates = setOf(List(machine.joltage.size) { 0 })
                while (machine.joltage !in lightStates) {
                    lightStates = lightStates.flatMap { oldState ->
                        machine.buttons.map { button ->
                            val nextState = oldState.toMutableList()
                            button.forEach { nextState[it]++ }
                            nextState
                        }
                    }.toSet()
                    buttonCount++
                }
                buttonCount
            }.toString()
        }
    }
}

fun main() {
    val p1ex1 = ExamplePuzzle(
        """
        [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
        [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
        [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
    """.trimIndent(), "7"
    )
    Runner.solve(2025, 10, 1, Day10::part1, p1ex1)

    val p2ex1 = ExamplePuzzle(
        """
        [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
        [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
        [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
    """.trimIndent(), "33"
    )
    Runner.solve(2025, 10, 2, Day10::part2, p2ex1)
}

