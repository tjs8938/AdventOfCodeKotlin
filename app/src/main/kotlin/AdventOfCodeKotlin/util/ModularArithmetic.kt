package AdventOfCodeKotlin.util

data class ModularArithmetic(val modulus: Long) {

    fun add(x: Long, y: Long): Long {
        return (x + y) % modulus
    }
    
    fun multiply(x: Long, y: Long): Long {
        return when {
            x == 0L -> 0L
            x == 1L -> y
            else -> {
                val half = multiply(x / 2, y)
                val twoHalf = add(half, half)
                if (x % 2L == 1L) add(twoHalf, y) else twoHalf
            }
        }
    }

    fun pow(x: Long, y: Long): Long {
        return when (y) {
            0L -> 1L
            1L -> x
            else -> {
                val half = pow(x, y / 2)
                val twoHalf = multiply(half, half)
                if (y % 2L == 1L) multiply(twoHalf, x) else twoHalf
            }
        }
    }

    fun divide(a: Long, b: Long): Long {
        val invB = pow(b, modulus - 2)
        return multiply(a, invB)
    }

    fun compose(g: Pair<Long, Long>, f: Pair<Long, Long>): Pair<Long, Long> {
        val (a, b) = f
        val (c, d) = g
        val newA = multiply(a, c)
        var newB = multiply(b, c)
        newB = add(newB, d)
        return Pair(newA, newB)
    }

    fun powCompose(f: Pair<Long, Long>, a: Long): Pair<Long, Long> {
        return when (a) {
            0L -> Pair(1L, 0L)
            1L -> f
            else -> {
                val half = powCompose(f, a / 2)
                val twoHalf = compose(half, half)
                if (a % 2L == 1L) compose(twoHalf, f) else twoHalf
            }
        }
    }

    fun invertFunction(f: Pair<Long, Long>, x: Long): Long {
        return divide(add(x, -f.second), f.first)
    }

    fun evaluate(f: Pair<Long, Long>, x: Long): Long {
        return add(multiply(f.first, x), f.second)
    }
}

fun main() {
    val modCalc = ModularArithmetic(7)
    require(modCalc.add(6, 5) == 4L)
    require(modCalc.multiply(6, 5) == 2L)
    require(modCalc.pow(2, 5) == 4L)
    require(modCalc.divide(5, 3) == 4L)
}