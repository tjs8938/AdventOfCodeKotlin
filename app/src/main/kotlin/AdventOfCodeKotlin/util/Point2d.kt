package AdventOfCodeKotlin.util

class Point2d(val x: Long, val y: Long) {
    operator fun plus(other: Point2d) = Point2d(x + other.x, y + other.y)


    override fun toString(): String {
        return "Point2d(x=$x, y=$y)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point2d

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}