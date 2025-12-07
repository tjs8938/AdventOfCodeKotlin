package AdventOfCodeKotlin.util

fun gcd(a: Int, b: Int): Int {
    return if (b == 0) a else gcd(b, a % b)
}

fun gcd(a: Long, b: Long): Long {
    return if (b == 0L) a else gcd(b, a % b)
}

fun gcd(a: ULong, b: ULong): ULong {
    return if (b == 0UL) a else gcd(b, a % b)
}

fun lcm(a: Int, b: Int): Int {
    return a * b / gcd(a, b)
}

fun lcm(a: Long, b: Long): Long {
    return a * b / gcd(a, b)
}

fun lcm(a: ULong, b: ULong): ULong {
    return a * b / gcd(a, b)
}