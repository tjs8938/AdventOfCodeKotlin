package AdventOfCodeKotlin.util

enum class Direction(val dy: Int, val dx: Int) {
    NORTH(-1, 0),
    EAST(0, 1),
    SOUTH(1, 0),
    WEST(0, -1)
    ;

    operator fun plus(loc: Pair<Int, Int>): Pair<Int, Int> {
        return loc.first + dy to loc.second + dx
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