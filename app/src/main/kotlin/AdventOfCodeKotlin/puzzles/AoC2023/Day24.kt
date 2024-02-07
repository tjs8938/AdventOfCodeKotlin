package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import com.microsoft.z3.Context
import com.microsoft.z3.Status
import java.math.BigDecimal


class Day24 {
    companion object {
//        fun part1(puzzle: PuzzleInputProvider): String {
//            return findIntersections(puzzle, 200000000000000L..400000000000000L).toString()
//        }

//        fun findIntersections(puzzle: PuzzleInputProvider, validRange: LongRange): Long {
//            val regex = Regex("""(-?[0-9]+)""")
//            val hailstones = puzzle.getAsString().map { s ->
//                Hailstone(regex.findAll(s).map { BigDecimal.valueOf(it.value.toLong()) }.toList())
//            }
//
//            var intersectionCount = 0L
//            hailstones.forEachIndexed { index, hs1 ->
//                hailstones.subList(index + 1, hailstones.size).forEach { hs2 ->
//                    val stoneA = hs1.copy()
//                    val stoneB = hs2.copy()
//
//                    // Solve for the set of equations:
//                    // stoneA.px + m * stoneA.vx = stoneB.px + n * stoneB.vx
//                    // stoneA.py + m * stoneA.vy = stoneB.py + n * stoneB.vy
//
//                    // Some hand-waving that I did with pen and paper:
//                    val p = (stoneB.py - stoneA.py) / stoneA.vy
//                    val q = (stoneB.px - stoneA.px) / stoneA.vx
//                    val r = stoneB.vx / stoneA.vx
//                    val s = stoneB.vy / stoneA.vy
//
//                    // First, confirm that the lines are not parallel
//                    if (r != s) {
//
//                        val n = (p - q) / (r - s)
//                        val m = q + n * r
//
//                        // the time for each hailstone when they would intersect must be in the future
//                        if (m > 0 && n > 0) {
//                            val posX = stoneA.px + m * stoneA.vx
//                            val posY = stoneA.py + m * stoneA.vy
//
//                            if (validRange.first.toDouble() <= posX && validRange.last.toDouble() >= posX &&
//                                validRange.first.toDouble() <= posY && validRange.last.toDouble() >= posY) {
//                                intersectionCount++
//                            }
//                        }
//                    }
//                }
//            }
//            return intersectionCount
//        }

        fun part2(puzzle: PuzzleInputProvider): String {
            /*
            solve([//math:a + r*b = 386183914429810 + r*6//],
                  [//math:a + s*b = 191853805235172 + s*205//],
                  [//math:a + t*b = 447902097938436 + t*-136//],
                  [//math:c + r*d = 203234597957945 + r*106//],
                  [//math:c + s*d = 96933297552275 + s*517//],
                  [//math:c + t*d = 262258252263185 + t*38//],
                  [//math:e + r*f = 537104238090859 + r*-164//],
                  [//math:e + s*f = 142797538377781 + s*229//],
                  [//math:e + t*f = 255543483328939 + t*89//])
             */

            val regex = Regex("""(-?[0-9]+)""")
            val hailstones = puzzle.getAsString().map { s ->
                Hailstone(regex.findAll(s).map { BigDecimal.valueOf(it.value.toLong()) }.toList())
            }

            val ctx = Context()
            val solver = ctx.mkSolver()
            val mx = ctx.mkRealConst("mx")
            val my = ctx.mkRealConst("my")
            val mz = ctx.mkRealConst("mz")
            val mxv = ctx.mkRealConst("mxv")
            val mvy = ctx.mkRealConst("mvy")
            val mzv = ctx.mkRealConst("mzv")
            repeat(3) {
                val (sx, sy, sz, sxv, syv, szv) = hailstones[it]
                val t = ctx.mkRealConst("t$it")
                solver.add(ctx.mkEq(ctx.mkAdd(mx, ctx.mkMul(mxv, t)), ctx.mkAdd(ctx.mkReal(sx.toString()), ctx.mkMul(ctx.mkReal(sxv.toString()), t))))
                solver.add(ctx.mkEq(ctx.mkAdd(my, ctx.mkMul(mvy, t)), ctx.mkAdd(ctx.mkReal(sy.toString()), ctx.mkMul(ctx.mkReal(syv.toString()), t))))
                solver.add(ctx.mkEq(ctx.mkAdd(mz, ctx.mkMul(mzv, t)), ctx.mkAdd(ctx.mkReal(sz.toString()), ctx.mkMul(ctx.mkReal(szv.toString()), t))))
            }
            if (solver.check() == Status.SATISFIABLE) {
                val model = solver.model
                val solution = listOf(mx, my, mz).sumOf { model.eval(it, false).toString().toLong() }
                return solution.toString()
            }
            return ""
        }
    }

    data class Hailstone(var px: BigDecimal, var py: BigDecimal, var pz: BigDecimal,
               var vx: BigDecimal, var vy: BigDecimal, var vz: BigDecimal) {
        constructor(i : List<BigDecimal>) : this(i[0], i[1], i[2], i[3], i[4], i[5]) {

        }
    }
}


fun main() {

    val ex1 = ExamplePuzzle("""
        19, 13, 30 @ -2,  1, -2
        18, 19, 22 @ -1, -1, -2
        20, 25, 34 @ -2, -2, -4
        12, 31, 28 @ -1, -2, -1
        20, 19, 15 @  1, -5, -3
    """.trimIndent())

//    Day24.findIntersections(ex1, 7L..27L).let {
//        println(it)
//        assert (it == 2L)
//    }
//
//    Runner.solve(2023, 24, part1 = Day24::part1)
    Runner.solve(2023, 24, part2 = Day24::part2)
}