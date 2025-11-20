package AdventOfCodeKotlin.util

open class Mover<V, T: Node<V>>(var loc: Pair<Int, Int>, var dir: Direction, val graph: Graph<V, T>) {
    fun move(dir: Direction) {
        loc = dir + loc
    }

    fun moveForward() {
        move(dir)
    }

    fun canMove(dir: Direction): Boolean {
        return graph.containsKey((dir + loc))
    }

    fun canMoveForward(): Boolean {
        return canMove(dir)
    }

    fun turnRight() {
        dir = dir.turnRight()
    }

    fun turnLeft() {
        dir = dir.turnLeft()
    }

    fun isAtEdge(): Boolean {
        return when(dir) {
            Direction.NORTH -> loc.first == 0
            Direction.EAST -> loc.second == graph.width - 1
            Direction.SOUTH -> loc.first == graph.height - 1
            Direction.WEST -> loc.second == 0
        }
    }

    override fun toString(): String {
        return "Mover(loc=$loc, dir=$dir)"
    }
}