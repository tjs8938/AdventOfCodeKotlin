package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import java.math.BigDecimal
import java.math.BigInteger


class Day21 {
    companion object {

        class Monkey(val name: String, val job: String) {

            var op: ((BigInteger, BigInteger) -> BigInteger)? = null
            var otherMonkeys: Pair<String, String>? = null
            var simpleYell: BigInteger? = null

            init {
                if (job.toIntOrNull() != null) {
                    simpleYell = BigInteger(job)
                } else {
                    val pieces = job.split(" ")
                    otherMonkeys = pieces[0] to pieces[2]
                    op = when (pieces[1]) {
                        "+" -> { x, y ->
                            x + y
                        }
                        "-" -> { x, y ->
                            x - y
                        }
                        "*" -> { x, y ->
                            x * y
                        }
                        "/" -> { x, y ->
                            x / y
                        }
                        else -> {
                            null
                        }
                    }
                }
            }

            fun yell(others: Map<String, Monkey>): BigInteger {
                if (simpleYell != null) {
                    return simpleYell!!
                } else {
                    return op!!.invoke(
                        others[otherMonkeys!!.first]!!.yell(others),
                        others[otherMonkeys!!.second]!!.yell(others)
                    )
                }
            }
        }

        fun part1(puzzle: PuzzleInputProvider): String {

            val monkeys =
                puzzle.getAsString().map { it.split(": ") }.map { Monkey(it[0], it[1]) }
                    .associateBy { it.name }
            return monkeys["root"]!!.yell(monkeys).toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val monkeys =
                puzzle.getAsString().map { it.split(": ") }.map { Monkey(it[0], it[1]) }
                    .associateBy { it.name }.toMutableMap()

            monkeys["root"] = monkeys["root"]!!.let {
                val pieces = it.job.split(" ")
                val newJob = "${pieces[0]} - ${pieces[2]}"
                Monkey("root", newJob)
            }

            var myNumber = BigInteger.ONE
            val two = BigInteger.valueOf(2L)
            val predicate: (BigInteger) -> Boolean = if (monkeys["root"]!!.yell(monkeys) > BigInteger.ZERO) {
                {x -> x > BigInteger.ZERO}
            } else {
                {x -> x < BigInteger.ZERO}
            }
            while (predicate.invoke(monkeys["root"]!!.yell(monkeys))) {
                myNumber *= two
                monkeys["humn"]!!.simpleYell = myNumber
            }

            var lower = myNumber / two
            var upper = myNumber
            var rootMonkeyVal = monkeys["root"]!!.yell(monkeys)
            while (rootMonkeyVal != BigInteger.ZERO) {
                myNumber = (lower + upper) / two
                rootMonkeyVal = calc(monkeys, myNumber)
                if (predicate.invoke(monkeys["root"]!!.yell(monkeys))) {
                    lower = myNumber
                } else {
                    upper = myNumber
                }
            }

            while (true) {
                if (calc(monkeys, myNumber - BigInteger.ONE) == BigInteger.ZERO) {
                    myNumber -= BigInteger.ONE
                } else {
                    break
                }
            }

            return myNumber.toString()
        }

        private fun calc(
            monkeys: MutableMap<String, Monkey>,
            myNumber: BigInteger?
        ): BigInteger {
            monkeys["humn"]!!.simpleYell = myNumber
            return monkeys["root"]!!.yell(monkeys)
        }
    }


}


fun main() {

    val example = ExamplePuzzle("root: pppw + sjmn\n" +
        "dbpl: 5\n" +
        "cczh: sllz + lgvd\n" +
        "zczc: 2\n" +
        "ptdq: humn - dvpt\n" +
        "dvpt: 3\n" +
        "lfqf: 4\n" +
        "humn: 5\n" +
        "ljgn: 2\n" +
        "sjmn: drzm * dbpl\n" +
        "sllz: 4\n" +
        "pppw: cczh / lfqf\n" +
        "lgvd: ljgn * ptdq\n" +
        "drzm: hmdt - zczc\n" +
        "hmdt: 32")

    assert(Day21.part1(example) == "152")

    Runner.solve(2022, 21, part1 = Day21::part1)

    assert(Day21.part2(example) == "301")
    Runner.solve(2022, 21, part2 = Day21::part2)
}

