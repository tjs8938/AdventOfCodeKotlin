package AdventOfCodeKotlin.puzzles.EverybodyCodesStory02

import AdventOfCodeKotlin.puzzles.EverybodyCodesStory02.Quest01.Companion.part1
import AdventOfCodeKotlin.puzzles.EverybodyCodesStory02.Quest01.Companion.part2
import AdventOfCodeKotlin.puzzles.EverybodyCodesStory02.Quest01.Companion.part3
import AdventOfCodeKotlin.util.Combinatorics
import AdventOfCodeKotlin.util.Graph
import AdventOfCodeKotlin.util.Graph.Companion.buildGraph
import AdventOfCodeKotlin.util.Node
import kotlin.math.max

fun main(args: Array<String>) {
    part1()
    part2()
    part3()
}

class Quest01 {
    companion object {
        fun part1() {
            val (board, instructions) = """
                *.*.*.*.*.*.*.*.*
                .*.*.*.*.*.*.*.*.
                *.*.*...*.*.*.*.*
                .*.*.*.*.*.*.*.*.
                *.*.*.*.*.*.*.*.*
                .*.*...*.*.*.*.*.
                ..*.*.*...*.*.*.*
                .*.*...*.*.*.*.*.
                *.*.*.*.*.*.*.*.*
                .*.*.....*.*.*.*.
                *.*.*.*.*.*.*.*.*
                .*.*.*.*.*.*.*.*.

                RRLLRLLRRLRR
                RRRLRRRLLLRR
                RLLLRRLLLRLL
                RLLRRLRLLLLR
                RRLRRLRRRRLR
                LRRLRLLRLLLR
                LLLRRRLRLRLR
                RRRRLLRRLLRR
                LLRRLRLLRRLL
            """.trimIndent().split("\n\n").map { it.lines() }

            val grid = buildGraph(board.map { it.toCharArray().toList() }, ::Node, includePredicate = { it != '*' })
            val result = instructions.mapIndexed { index, line ->
                val dest = destForToss(index, board.size, grid, line)
                //println("Toss: ${index + 1} -> to ${dest / 2 + 1} for ${(dest / 2 + 1) * 2 - (index + 1)} coins")
                max((dest / 2 + 1) * 2 - (index + 1), 0)
            }.sum()
            println("Part 1: $result")
        }

        private fun destForToss(
            tossSlot: Int,
            boardHeight: Int,
            grid: Graph<Char, Node<Char>>,
            line: String
        ): Int {
            var current = -1 to (tossSlot * 2)
            var counter = 0
            while (current.first < boardHeight - 1) {
                current = current.first + 1 to current.second
                if (grid.containsKey(current)) {
                    continue
                }
                val move = when (line[counter]) {
                    'L' -> -1
                    'R' -> 1
                    else -> 0
                }
                current = current.first to (current.second + move)
                counter++

                if (grid.containsKey(current)) {
                    continue
                } else {
                    current = current.first to current.second - (move * 2)
                }
            }
            return current.second
        }

        fun part2() {
            val (board, instructions) = """
                *.*.*.*.*.*.*.*.*.*.*.*.*
                .*.*.*.*.*.*.*.*.*.*.*.*.
                *.*...*...*.*.*.*.*.*.*.*
                .*...*.*.*.*.*.*.*.*.*...
                *.*...*.*.*.*.*.*.*.*.*.*
                .*.*.*.*.*.*.*.*.*.*.*.*.
                *...*.*.*.*.*.*.*.*.*.*.*
                ...*.*.*.*...*.*.*.*...*.
                *.*.*.*.*.*.*.*.*.*.*.*.*
                ...*.*.*.....*.*.*...*...
                *.*.*.*.*.*...*...*.*.*..
                .*.*.*.*.*...*.*.*.*...*.
                *.*.*.*.*.*.*.*.*.*.*.*.*
                ...*.*.*.*.*.*.*.*...*...
                *.*.*.*.*.*...*.*.*.*.*.*
                .*.*...*...*...*.*.*.*.*.
                *.*.*.*.*.*...*.*.*.*.*.*
                .*...*.*.*.*.*.*.*.*.*.*.
                *.*.*.*.*.*.*.*.*.*.*.*.*
                .*.*.*.*.*.*.*.*.*.*.*.*.

                RRRRLRRRLRRLRRLLRLRR
                LRRRRRRLLLRRRRRRRRRR
                LLRLRLLLRLLLRRLLRRRR
                LRLRLLRLRRRRRRLLLLRL
                RLLRLLRRLRRRRRRRRRRR
                LRLLLRLLRLLLLLLLLRLL
                RLRLRLRLLLRLLLLRRRLR
                RRRRRRRLRRLRRRRRLRLR
                LLLRLLLLLLLLLLLLLRLL
                LLRRLRRLLRLRRLRRLLLR
                RRLRLLRRRRRRRRRRLLLR
                RLLLLRLRRRLLLLLLRLLL
                RLRRLRLLRRLRLRLRRLRR
                RRLRRRRRRRRRRRRRRRLR
                LLLLLLLLRLLRLLLRLLLL
                LRRRLRRLLLRLRRLRLLRR
                LRRRRRRLRRRRRRRRRRRR
                LLLRRLLLLLLLLLLLLLLR
                LLRRLLLRRLLLLLRRRRLL
                LRRRRRRRRLLRRLRRRRLL
                LLRRLLLRLLRRRLLLRLLL
                RLLLLLLLLRRLRLLRLLRR
                RRRLRRLRRRRRRRRRLRRR
                LLLLRRRRLLLLLLLLLLLL
                LLLRLLRLRRLLRLLRRLLR
                RLRLRRRRLRLLLLLRRRRL
                LLRLRLLRLLLLRLLRRLLL
                LLLRRLRRLRLLRLRLLLRR
                RLRRRRRRRRLRRLLRRRRR
                LLLLLLLLLLLRLLRRLLLL
                LRRRLRLLRLRRRRLLLRLR
                LLRRRRRRRRRLRRRRLLRR
                LLLLLLLLLLLLRLRLRLLL
                RLLRLRLLRLRRRRRRLLRL
                RRLRLRRRLRRLLLRRRRRL
                LLRLLLRRLLRRLLLRLLLL
                LRLLLLRRRRRLLLRRLLLL
                LLRLRRRRRRRRRRRRLRRR
                RLLLLLLLLRRLLLLLLLLL
                RLRRLLRLLRLRLRLRRRRR
                RRLLRRRRRLRRLRRLRLRR
                RLLLLLLRRLLLRLLLLLRL
                RRRRRRRLRLRRLRRRLRRR
                LLRRRRLRRLRRRRRRRRRR
                LRLLRRLLLLLRLRLLLLLR
                RLRLRRRLLLRLLRLRLLRL
                RRRLRRRRLRRRRLRRRRRR
                LLLLLLLLRLLLLLRLLLLL
                LLRRLRLRRLLRRRRRLLRL
                RLRRRRRRRLRRRRLRRRRR
                LLLLRRLLRLLLRLRRLLLR
                LLRRRRRRLLRLLRLLRLRL
                LRLRRRRLRRRLLRLLRRRR
                LLLRLLLLLRRLLLLLLLLL
                LLRRRRRLRLRLRRRRRLRL
                LRRRRRRLRRLLRRRRRRLL
                LLLRLLLRRLLLLLLLLLLL
                RRRLLRLRRRLLRLLRLLLL
                RRRLRLRLRRRRRRLRRRLR
                LRLLLLLLLLRLLLLLLLLL
                LRLRRLRRLLRLRLRRRRRL
                LLLRLLLRLRRRRRLLLRRR
                LRLRLLLLLRRLLRLLRLLL
                LRRLRLLRRLRLRLRLRLLR
                RRRRRRRLRLRLRLRRRRRR
                LRLLLLRLLLLLLLLLLLLL
                LRLLRLRLRRLLLLLRLLLL
                RRRLRRRLRLRRLLRRRLRR
                LLRLLRLLLRLLLLLLLLLR
                LRRRRLLLRLLLRRRRRRRL
                RRRRRRLRRLRRRLLLRRLR
                LLLRRLLLLLLLRLLRLLLL
                RLRLRRLRRLLRRLLRRRRR
                LLRRLRLRRRRRRRRRRRLR
                LLLLLLLLLLLLLLLRLLLL
                RLRLRLRRLRRLLLLRLLRL
                RRRRRRRLRRRRRRLRLRRR
                LRLLLLLLLLLLLLLLLRLL
                LRRRLLRLRRLLLLLLLLLL
                RRRRLRRRLRRRRLRRRRRR
                RLLRLLLLLLLLLRLRLLLL
                RLLRLLLRLLLRRLLRLLRL
                RLRRLRRRRRLRRRRLRRRR
                LRRLLLLLRLLLRRLLRLLL
                LRRLLLLLRLLLLRLLRRLL
                RRRRRRRLRRRRRRRRLRRR
                RLLLLLLLLRRLLLLLLLRL
                LRLLLRRRLRRRRLRLRLRL
                RRRRRRRRRRRLRRRRRRRR
                RLLLRLRLRLLLLLLLLLRL
                LLRRLLRLRRLRLRRLRRRL
                RRLRRLRRRLLRLRLRRRLR
                LLLRRRRLLLLLLLLLLLLL
                LLRRRRRLLRRLLLRLRLLL
                RRRRLRRRRLRRRLRRRRLR
                RLLLRRLLRLRRLRLLLLRL
                LLLLRLRRRRRRLRLLRLLR
                RRRRRRLRRRRRLRRRLRRR
                LLLLLLLLLLLLLLLLLLLL
                RRRRRRRRRRRRRRRRRRRR
            """.trimIndent().split("\n\n").map { it.lines() }

            val grid = buildGraph(board.map { it.toCharArray().toList() }, ::Node, includePredicate = { it != '*' })
            val result = instructions.mapIndexed { index, line ->
                val slotToScore = (0 until (board[0].length + 1) / 2).map {
                    val dest = destForToss(it, board.size, grid, line)
//                    println("Toss: ${index + 1} -> from ${it + 1} to ${dest / 2 + 1} for ${(dest / 2 + 1) * 2 - (it + 1)} coins")
                    (it + 1) to max((dest / 2 + 1) * 2 - (it + 1), 0)
                }.maxBy { it.second}
//                println("Toss: ${index + 1} -> from ${slotToScore.first} for ${slotToScore.second}} coins")
                slotToScore.second
            }.sum()
            println("Part 2: $result")
        }

        data class Token(val scores: List<Pair<Int, Int>>, var counter: Int = 0) {
            fun currentScore(): Int {
                return scores[counter].second
            }
            fun currentSlot(): Int {
                return scores[counter].first
            }

            fun advance() {
                counter++
            }

            fun retreat() {
                counter--
            }
        }


        fun part3() {
            val (board, instructions) = """
                *.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
                .*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.
                *.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
                .*.*...*.*.*.*.*.*.*.*.*.......*.*.*.*.
                *.*.*.*.*.*.*...*.*...*.....*.*.*.*.*.*
                .*.*.*.*.*...*.*.*.*...*.*...*.*.*.*.*.
                ....*.*.*...*...*...*.*.*.*.*.*.*.*.*.*
                .*.*.*.*.*.*.*.*.*.*...*.*...*.*.*.*.*.
                *.*...*.*.*.*.*...*.*.*...*.*.*...*.*.*
                .*...*.*.*.*.*.*.*...*.*.*.*.*.*...*...
                *.*...*...*.*...*.......*...*.*.*...*.*
                ...*.*.*...*.*.*...*...*...*.*...*...*.
                *.*.*.....*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
                .*.*.*...*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.
                *.*.*.*...*...*.*.*.*...*.*.*.*.*.*...*
                .*.*.*.*.*.*.*.*.*.*.*.*...*.*.*.*...*.
                *.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*..
                .*...*.....*...*.*.*.*.*.*.*.*.*.*.*...
                *.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
                .*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.

                RRRRRLRRLRRLRRRRRLRL
                RRLRRRRRRLRRLRLRRRRR
                LLLLLLLLRRLLRLLLLLLL
                LLLRLRRLRLRRLLRRRRLR
                RLLRLRRRRRRRRRRRRRRR
                LLLLRLLLRLLLLLLLLLLL
            """.trimIndent().split("\n\n").map { it.lines() }

            val grid = buildGraph(board.map { it.toCharArray().toList() }, ::Node, includePredicate = { it != '*' })
            val result = instructions.mapIndexed { index, line ->
                (0 until (board[0].length + 1) / 2).map {
                    val dest = destForToss(it, board.size, grid, line)
//                    println("Toss: ${index + 1} -> from ${it + 1} to ${dest / 2 + 1} for ${(dest / 2 + 1) * 2 - (it + 1)} coins")
                    (it + 1) to max((dest / 2 + 1) * 2 - (it + 1), 0)
                }.sortedBy { it.second}
            }

            var tokens = result.map { Token(it) }
            val minScore = findMinScore(tokens)
            tokens = result.map { Token(it.reversed()) }
            val maxScore = findMaxScore(tokens)
            println("Part 3: $minScore $maxScore")
        }

        private fun findMinScore( tokens: List<Token> ) : Int {
            return findScore(tokens) { a, b -> a < b }
        }

        private fun findMaxScore( tokens: List<Token> ) : Int {
            return findScore(tokens) { a, b -> a > b }
        }

        private fun findScore(
            tokens: List<Token>,
            comparator: (Int, Int) -> Boolean
        ): Int {
            if (tokens.map { it.currentSlot() }.toSet().size == 6) {
                return tokens.sumOf { it.currentScore() }
            } else {
                val conflictedPair = Combinatorics.combinations(tokens, 2).first { it[0].currentSlot() == it[1].currentSlot() }
                val tokenA = conflictedPair[0]
                val tokenB = conflictedPair[1]
                tokenA.advance()
                val scoreA = findScore(tokens, comparator)
                tokenA.retreat()
                tokenB.advance()
                val scoreB = findScore(tokens, comparator)
                tokenB.retreat()
                return if (comparator(scoreA, scoreB)) scoreA else scoreB
            }
        }
    }
}