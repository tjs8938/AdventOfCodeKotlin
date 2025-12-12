package AdventOfCodeKotlin.util

import kotlin.math.max
import kotlin.math.min

data class Line(val p1: Point2d, val p2: Point2d) {

    val minY: Long get() = min(p1.y, p2.y)
    val maxY: Long get() = max(p1.y, p2.y)
    val minX: Long get() = min(p1.x, p2.x)
    val maxX: Long get() = max(p1.x, p2.x)

    fun isHorizontal(): Boolean {
        return p1.y == p2.y
    }

    fun isVertical(): Boolean {
        return p1.x == p2.x
    }
}