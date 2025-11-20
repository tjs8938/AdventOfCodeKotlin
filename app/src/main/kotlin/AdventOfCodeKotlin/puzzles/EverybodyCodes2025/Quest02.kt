package AdventOfCodeKotlin.puzzles.EverybodyCodes2025

fun main(args: Array<String>) {
    Quest02.part1()
    Quest02.part2()
    Quest02.part3()
}

class Quest02 {

    data class Complex(val x: Long, val y: Long) {
        fun add(other: Complex): Complex {
            return Complex(x + other.x, y + other.y)
        }

        fun subtract(other: Complex): Complex {
            return Complex(x - other.x, y - other.y)
        }

        fun multiply(other: Complex): Complex {
            return Complex(x * other.x - y * other.y, x * other.y + y * other.x)
        }

        fun divide(other: Complex): Complex {
            return Complex(x / other.x, y / other.y)
        }

        override fun toString(): String {
            return "[${x},${y}]"
        }

        companion object {
            fun fromString(s: String): Complex {
                val regex = """\[\s*(-?\d+)\s*,\s*(-?\d+)\s*]""".toRegex()
                val matchResult =
                    regex.matchEntire(s) ?: throw IllegalArgumentException("Invalid complex number format: $s")
                val (xStr, yStr) = matchResult.destructured
                return Complex(xStr.toLong(), yStr.toLong())
            }
        }
    }

    companion object {
        fun part1() {
            val a = Complex.fromString("[149,55]")
            var r = Complex(0, 0)

            repeat(3) {
                r = r.multiply(r)
                r = r.divide(Complex(10, 10))
                r = r.add(a)
            }

            println(r)
        }

        fun part2() {
            var result = engravePoints()
            println("Part 2 results: $result")
        }

        private fun engravePoints(stepSize: Long = 10L): Int {
            val a = Complex.fromString("[-79057,14068]")
            var good = mutableListOf<Complex>()
            var bad = mutableMapOf<Complex, Int>()
            var result = 0
            for (x in a.x..a.x + 1000 step stepSize) {
                for (y in a.y..a.y + 1000 step stepSize) {
                    val c = Complex(x, y)
                    var r = Complex(0, 0)
                    var increasedX = 0
                    var increasedY = 0

                    for (i in 0 until 100) {
                        if (c in good) {
                            result++
                            break
                        }
                        if (c in bad && bad[c]!! <= i) {
                            bad[c] = i
                            break
                        }
                        val p = r
                        r = r.multiply(r)
                        r = r.divide(Complex(100000, 100000))
                        r = r.add(c)
                        if (r.x < -1000000 || r.x > 1000000 || r.y < -1000000 || r.y > 1000000) {
                            bad[c] = i
                            break
                        }
                        val diff = r.subtract(p)
                        if (diff.x > 0) {
                            increasedX++
                        } else {
                            increasedX = 0
                        }
                        if (diff.y > 0) {
                            increasedY++
                        } else {
                            increasedY = 0
                        }
                        if (increasedX >= 10 || increasedY >= 10) {
                            bad[c] = i
                            break
                        }
                    }
                    if (c !in bad) {
                        good.add(c)
                        result++
                    }
                }
            }
            return result
        }

        fun part3() {
            var result = engravePoints(1)
            println("Part 3 results: $result")
        }
    }
}