package AdventOfCodeKotlin.puzzles.AoC2025

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import AdventOfCodeKotlin.util.Combinatorics
import AdventOfCodeKotlin.util.MemoizedFunction.Companion.memoize
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.api.zeros
import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.all
import org.jetbrains.kotlinx.multik.ndarray.operations.any
import org.jetbrains.kotlinx.multik.ndarray.operations.minus
import org.jetbrains.kotlinx.multik.ndarray.operations.plus

infix fun D1Array<Int>.xor(other: D1Array<Int>): D1Array<Int> {
    var result = mk.zeros<Int>(this.size)
    for (i in this.indices) {
        result[i] = this.get(i) xor other.get(i)
    }
    return result
}

operator fun D1Array<Int>.rem(other: Int): D1Array<Int> {
    var result = mk.zeros<Int>(this.size)
    for (i in this.indices) {
        result[i] = this.get(i) % other
    }
    return result
}


class Day10 {
    companion object {

        fun div(array: D1Array<Int>, other: Int): D1Array<Int> {
            var result = mk.zeros<Int>(array.size)
            for (i in array.indices) {
                result[i] = array.get(i) / other
            }
            return result
        }

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

            val machineStrings = puzzle.getAsString()
            val machines = machineStrings.map { machine ->
                val joltage = machine.substring(machine.indexOfFirst { it == '{' })
                    .drop(1).dropLast(1).split(",").map { it.trim().toInt() }.let { mk.ndarray(it) }

                val buttons =
                    machine.substring(machine.indexOfFirst { it == '(' }, machine.indexOfLast { it == ')' } + 1)
                        .split(" ")
                        .map { it.trim().drop(1).dropLast(1).split(",").map { it.trim().toInt() } }
                        .map {
                            val signals = mk.zeros<Int>(joltage.size)
                            it.forEach { s -> signals[s] = 1 }
                            signals
                        }

                val buttonCombos: Map<D1Array<Int>, List<Pair<D1Array<Int>, Int>>> = (0..buttons.size).flatMap { n ->
                    Combinatorics.combinations(buttons, n).map { combo ->
                        val bitMap = combo.fold(mk.zeros<Int>(joltage.size)) { acc, button ->
                            acc xor button
                        }
                        val sum = combo.fold(mk.zeros<Int>(joltage.size)) { acc, button ->
                            acc + button
                        }
                        bitMap to (sum to n)
                    }
                }.groupBy({ it.first }, { it.second })

                val lowestButtons = memoize<D1Array<Int>, Long> { state: D1Array<Int> ->

                    if (state.all { it == 0 }) {
                        0L
                    } else if (state.any { it < 0 }) {
                        Long.MAX_VALUE
                    } else {
                        val bitmap = state % 2
                        if (bitmap in buttonCombos) {
                            val minButtons = buttonCombos[bitmap]!!.minOf { (buttonSums, buttonCount) ->
                                val reduceByButtons = state - buttonSums
                                val divTwo = div(reduceByButtons, 2)
                                val recurse = this(divTwo)
                                if (recurse == Long.MAX_VALUE) {
                                    Long.MAX_VALUE
                                } else {
                                buttonCount.toLong() + (recurse * 2L)
                                    }
                            }
//                            println("State: $state -> Min Buttons: $minButtons")
                            minButtons
                        } else {
                            Long.MAX_VALUE
                        }
                    }
                }

                lowestButtons(joltage)
            }



            return machines.sum().toString()
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

