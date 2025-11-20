package AdventOfCodeKotlin.puzzles.EverybodyCodesStory01

import AdventOfCodeKotlin.util.ModularArithmetic

fun main(args: Array<String>) {
    Quest03.part1()
    Quest03.part2()
    Quest03.part3()
}

class Quest03 {

    companion object {
        fun part1() {
            val input = """
                x=1 y=1
                x=2 y=2
                x=3 y=3
                x=4 y=4
                x=5 y=5
                x=6 y=6
                x=3 y=2
                x=4 y=7
                x=1 y=9
                x=9 y=2
            """.trimIndent()

            val regex = """x=(-?\d+)\s+y=(-?\d+)""".toRegex()
            val points = input.lines().sumOf {
                val matchResult = regex.matchEntire(it) ?: throw IllegalArgumentException("Invalid input line: $it")
                val (xStr, yStr) = matchResult.destructured
                val x = xStr.toInt() - 1
                val y = yStr.toInt() - 1

                val disc = x + y + 1
                val newX = (x + 100) % disc
                val newY = disc - newX - 1
                100 * (newY + 1) + (newX + 1)
            }
            println(points)
        }

        fun part2() {
            val input = """
                x=6 y=18
                x=45 y=9
                x=19 y=11
                x=49 y=23
                x=1 y=3
                x=5 y=1
                x=25 y=19
                x=45 y=17
                x=5 y=13
                x=6 y=2
            """.trimIndent()

            val regex = """x=(-?\d+)\s+y=(-?\d+)""".toRegex()
            val snails = input.lines().map {
                val matchResult = regex.matchEntire(it) ?: throw IllegalArgumentException("Invalid input line: $it")
                val (xStr, yStr) = matchResult.destructured
                val x = xStr.toInt() - 1
                val y = yStr.toInt() - 1

                val disc = x + y + 1
                (disc to y)
            }
            val biggestDiscSnail = snails.maxBy { it.first }
            val biggestDisc = biggestDiscSnail.first
            var days = biggestDiscSnail.second
            while (true) {
                if (snails.all { (disc, y) ->
                        (days - y) % disc == 0
                    }) {
                    println(days)
                    break
                }
                days += biggestDisc
            }

        }

        fun multInv(a: Long, b: Long): Long {
            if (b == 1L) return 1
            var aa = a
            var bb = b
            var x0 = 0L
            var x1 = 1L
            while (aa > 1) {
                val q = aa / bb
                var t = bb
                bb = aa % bb
                aa = t
                t = x0
                x0 = x1 - q * x0
                x1 = t
            }
            if (x1 < 0) x1 += b
            return x1
        }

        fun chineseRemainder(n: LongArray, a: LongArray): Long {
            val prod = n.fold(1L) { acc, i -> acc * i }
            var sum = 0L
            for (i in 0 until n.size) {
                val p = prod / n[i]
                sum += a[i] * multInv(p, n[i]) * p
            }
            return sum % prod
        }

        fun part3() {
            val input = """
                x=33 y=15
                x=29 y=3
                x=12 y=6
                x=8 y=12
                x=46 y=14
                x=44 y=28
                x=4 y=4
                x=1 y=5
                x=2 y=10
                x=3 y=41
            """.trimIndent()

            val regex = """x=(-?\d+)\s+y=(-?\d+)""".toRegex()
            val snails = input.lines().map {
                val matchResult = regex.matchEntire(it) ?: throw IllegalArgumentException("Invalid input line: $it")
                val (xStr, yStr) = matchResult.destructured
                val x = xStr.toInt() - 1
                val y = yStr.toInt() - 1

                val disc = x + y + 1
                (disc to y)
            }
            val discs = mutableListOf<Long>()
            val remainders = mutableListOf<Long>()
            for ((disc, y) in snails) {
                discs.add(disc.toLong())
                remainders.add(y.toLong())
            }
            val result = chineseRemainder(discs.toLongArray(), remainders.toLongArray())
            println(result)

        }
    }
}
