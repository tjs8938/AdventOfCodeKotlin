package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import java.math.BigInteger


class Day11 {
    companion object {
        private fun parseInput(puzzle: PuzzleInputProvider): List<Monkey> {
            val monkeys: List<Monkey> = puzzle.get().split("\n\n").map {
                it.split("\n").map { it.trim() }.slice(1..5)
                    .let { (starting, operation, test, positive, negative) ->
                        {
                            val items = starting.replace("Starting items: ", "").split(", ")
                                .map { it.toBigInteger() }
                            val op = Monkey.parseOperation(
                                operation.replace(
                                    "Operation: new = old ",
                                    ""
                                )
                            )
                            val t = test.replace("Test: divisible by ", "").toBigInteger()
                            val p =
                                positive.replace("If true: throw to monkey ", "").toInt()
                            val n =
                                negative.replace("If false: throw to monkey ", "").toInt()
                            Monkey(ArrayDeque(items), t, op, p, n)
                        }
                    }.invoke()
            }
            return monkeys
        }

        fun part1(puzzle: PuzzleInputProvider): String {
            val monkeys: List<Monkey> = parseInput(puzzle)

            repeat(20) {
                monkeys.forEach { it.inspect(monkeys) { i -> i.divide(BigInteger("3"))} }
            }

            return monkeys.sortedByDescending { it.inspectionCount }
                .slice(0..1)
                .let { (m1, m2) -> m1.inspectionCount * m2.inspectionCount }.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val monkeys: List<Monkey> = parseInput(puzzle)

            val prodOfTests = monkeys.map { it.test }.reduce(BigInteger::times)

            repeat(10000) {
                monkeys.forEach { it.inspect(monkeys) { i -> i.mod(prodOfTests)} }
            }

            return monkeys.sortedByDescending { it.inspectionCount }
                .slice(0..1)
                .let { (m1, m2) -> m1.inspectionCount * m2.inspectionCount }.toString()
        }
    }
}


fun main() {
    Runner.solve(2022, 11, part1 = Day11::part1)
    Runner.solve(2022, 11, part2 = Day11::part2)
}

data class Monkey(
    var items: ArrayDeque<BigInteger>,
    val test: BigInteger,
    val operation: (BigInteger) -> BigInteger,
    val posDest: Int,
    val negDest: Int
) {
    var inspectionCount: BigInteger = BigInteger.ZERO

    fun inspect(monkeys: List<Monkey>, relief: (BigInteger) -> BigInteger) {
        inspectionCount += BigInteger(items.size.toString())

        while (items.isNotEmpty()) {
            var i = items.removeFirst()
            i = operation.invoke(i)
            i = relief(i)
            val target = if (i.mod(test) == BigInteger.ZERO) {
                posDest
            } else {
                negDest
            }
            monkeys[target].items.add(i)
        }
    }

    companion object {
        fun parseOperation(op: String): (BigInteger) -> BigInteger {
            return op.split(" ").let { (operation, value) ->
                if (value == "old") {
                    { it * it }
                } else {
                    if (operation == "+") {
                        { it + value.toBigInteger() }
                    } else {
                        { it * value.toBigInteger() }
                    }
                }
            }
        }
    }
}