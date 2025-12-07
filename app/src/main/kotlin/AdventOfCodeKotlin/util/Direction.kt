package AdventOfCodeKotlin.util

enum class Direction(val dy: Int, val dx: Int) {
    NORTH(-1, 0),
    EAST(0, 1),
    SOUTH(1, 0),
    WEST(0, -1)
    ;

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
        return this.first + other.first to this.second + other.second
    }

    operator fun Pair<Int, Int>.plus(dir: Direction): Pair<Int, Int> {
        return this.first + dir.dy to this.second + dir.dx
    }

    operator fun plus(loc: Pair<Int, Int>): Pair<Int, Int> {
        return loc.first + dy to loc.second + dx
    }

    operator fun times(mult: Int): Pair<Int, Int> {
        return dy * mult to dx * mult
    }

    fun turnRight(): Direction {
        return when (this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
        }
    }

    fun turnLeft(): Direction {
        return when (this) {
            NORTH -> WEST
            WEST -> SOUTH
            SOUTH -> EAST
            EAST -> NORTH
        }
    }

    companion object {
        fun fromChar(c: Char): Direction {
            return when (c) {
                '^' -> NORTH
                '>' -> EAST
                'v' -> SOUTH
                '<' -> WEST
                else -> throw IllegalArgumentException("Invalid direction character: $c")
            }
        }
    }
}