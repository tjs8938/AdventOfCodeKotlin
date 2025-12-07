package AdventOfCodeKotlin.util

import AdventOfCodeKotlin.puzzles.AoC2022.plus

open class Mover<V, T: Node<V>>(var loc: Pair<Int, Int>, var dir: Direction, val graph: Graph<V, T>? = null) {
    fun move(dir: Direction, count: Int = 1) {
        loc += (dir * count)
    }

    fun moveForward(count: Int = 1) {
        move(dir, count)
    }

    fun canMove(dir: Direction): Boolean {
        return graph?.containsKey((dir + loc)) ?: true
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
        if (graph == null) return false
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