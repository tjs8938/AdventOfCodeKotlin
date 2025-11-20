package AdventOfCodeKotlin.puzzles.EverybodyCodes2025

import AdventOfCodeKotlin.util.Graph.Companion.buildGraph
import AdventOfCodeKotlin.util.MemoizedFunction
import AdventOfCodeKotlin.util.MemoizedFunction.Companion.memoize
import AdventOfCodeKotlin.util.Node

class Quest10 {
    companion object {
        fun part1() {
            val input = """
                .SSSS.S.SS.S.SSSSS..S
                .S.SS.SSS..SSSSSSSSS.
                .SSSSS.SS.S..S.S..SSS
                S.S..SSS...SSSSS..SS.
                SSS.SSSSSSSSS..S...SS
                SSSSSSSSS.S.SSS.SSS.S
                SS.SSSSS.SSSSS.SSS.S.
                SSSSSSSSSSS.SSSSSSS.S
                SSS.S.SSS.SSSS.SS..SS
                SSSSSSS.SSSSS..SSSSSS
                SSSSS..SS.DS.SSSS.S.S
                .SS.S..SSSSSSSSSSSSSS
                .SS.SSS.SS.SS.SS.SSS.
                SSSSSSSSSSSS.S.S.S.SS
                .SS..SS.S.SS.SSSS..SS
                S..SSSS.S.S.SS..SSSSS
                SSSSSSSSSSSS..SS.SS.S
                S..SSSSSSSSSSSSSSSSS.
                S.SSS.SSS.SSSSS....SS
                S.SS.S..SSSSSSSSSSSSS
                SSSSSSSSSSSSS.SSSSS.S
            """.trimIndent().lines().map { it.toList() }
            val TOTAL_MOVES = 4
            var tempStart: Node<Char>? = null
            val graph = buildGraph(
                input, ::Node,
                callbacks = mapOf('D' to { _, n -> tempStart = n })
            )
            val start = tempStart!!

            val visited = mutableSetOf(start.y to start.x)
            var toProcess = mutableSetOf(start.y to start.x)
            var moves = 0
            while (toProcess.isNotEmpty() && moves < TOTAL_MOVES) {
                toProcess = toProcess.flatMap { next ->
                    listOf(
                        2 to 1, 2 to -1, -2 to 1, -2 to -1,
                        1 to 2, 1 to -2, -1 to 2, -1 to -2
                    ).map { (dy, dx) ->
                        next.first + dy to next.second + dx
                    }.filter {
                        val node = graph[it]
                        node != null && (it) !in visited
                    }
                }.toMutableSet()
                visited.addAll(toProcess)
                moves++
            }

            visited.count { graph[it]!!.value == 'S' }
                .let { println(it) }
        }

        fun part2() {
            val input = """
                #S#S.SSS.#SS#S#S.#S.#S#####SS#.S.##.#.SS.#..SSS#..###S.#.#.SS.SS.S....SS#.SS.S#..#S.SS..###S#S.S.S#..
                SS...SS..S##S##..SS.......SSS...SS.##.SSS#SS.S##.S.S.S...S.#SS..S...#S.#.S.S..SSSS...#S.#S..SS...S...
                #.S#S#......S.#..S#..S#..##SSSSS....SS.SS.SS#S.S#SS.S.#.#..S..#S..#.##SS.S.#.SS#S#.#S#S.#....#.S..##S
                S#.#........S...#SS#.S...#.S..#.SS#.SSS#.#..#.SS#.S..#S...S...##.....S#..#.#SS.#.#S#.#.#......#S.SS.#
                .#S#SSS..#SSSS.....SS..SSSS..#S..S##.S....S#S.SS..#.###.S.#.#..SS..S##.S.....#S#....#.S..##.S.S...SS#
                .S.S.......#.#..S#S#.S#.SS.#...S#.SS##S....#.S#..S#.#..SSSSS...S.#S.#.#.SS.#.#SS.#S#S...##S.S.S.SSS..
                #.S.#S....##.S.S#.SSS..##S...S#.#S..#.S#SSS#SSS.#.##.S..S..S##SSSS.SSS..SS.S..#.#.S##SS#.S..#..#S.#.S
                S#.SSSS#.S..S.SS..SSSS#...SSSSS####.S#...#S.S.##..S.S..S#.SSS...##.SS.#..#...#..S#.S#.SSSS#.SS.##.#SS
                S.#S.S....S#.#.S.#S.S.....##.S.....#...S.#S..S.#.......##..#.S##....#..SSS.....SS..S#SS.S..S.S.SS..##
                ...SSS###S.......S.S.S..S#..S..SS..##SSS#S.SSSS#S.S....SSSSS.#S...#..#S#S###.S...S..S....SS##..S....S
                S#SSSS.S..#.S.S..S.S##..S#S.S#S.#.#....S##..S..SS#SSSS###SS.#SS..##.S#S.S.#.....#SS...SS.S#.#...S#.#S
                #SS..SSS#S#...S....#S##.S.S#S.#S.S..S..SS...SSS#.#S..SSS.#....S..#.#S.S.SS##.S#....SS.S#S..#SS.#SSS.S
                ..S...#S..SS#SSS..S..SSS..SSSS.##S.S.#.S#.S.#.#S#.S..#S#S.S...S..S..S..###SS.#.#####.SS#..#S.#SSS#.S.
                SS#.#.#S#.S..SSS#SS##..#SS.#S.##....#S##.SSSSSS....#SS#..SS##SS...S.#.#SS.SSS...SS#S#.SSS.S.#S#SSSS..
                .....SS#.#.#SS...#S.S.#S##SS###.SS.SS.#.S..S#SS..#..#SSSS...SSS.S.....#..S..SS#.SS.#S#S.##.S#S#SSS#S.
                #S#S#...SSS..SS.#.#....#..S..SS...#.S##SSSSS..S.#S.S#.S.##..#...SSSSS.S...#.SSSSS#.#SS..S..S..S..S..S
                .#S...SS.SS.S...S.SS.#S.#S#.#......S.#.S#S#..#SSS......S.#..S#.SS......S.#...#S#S#....#.##.#......S.#
                .##.S##.S.#..S...S#.#.#SSSSS.#.#.S#SS#S..S.SSS.S.SSS.#.SSS.SS.#.S#..S.S#..SS.#..#..SSSSS##..S#.##.#.#
                .S..S.S#.#SS##S.S.#.###.#S#SS.#...SSS...#SS#..S##..#S.SS#..#SSS.#...S..SS.S....##SS.S.#SS#...S.#.#..S
                S#...S.#SS#.#S.#S.S..S....S.##SSS#.S#.S##.....#S.##.S.S..#S..#...##......##.#S.SS.S#.S..##.#.SS#.S#SS
                .S.S.S##...#SS.....#SS.S..##....#S.S#.#SS...#..S.....#.#...S..S.#..S.##SSS..SS###S#SSS.#SS#..S.#SS.#S
                .#SS.##.S..##...S.SS.S.S..SSSSS...S#....#...#.S.S#..##S..SSS.S.#S..SS...S.SSS#SS.#..S.SS#..S.SS###S..
                S.S...SS.SS.#SS.#....S####..SS.S......##S.S#.SS##.##SS###SS......SSS.S.#.SS.#S.#S.S..S###S##S##...SSS
                #S#S.S#S..#S.S.####SS.S.....#S..S#.S.S#SS#S##S.##...SSSSS#.S..####......S##...S..##..SS.S##S.##S..S##
                ###.S.#S..#..#.#SSSS#S#.S.###..#S.#...S###..#S.S#..S.SS.SS..#S...#####S##S.S..SS.S#.##S#.S......SS.#.
                ##...S.#....#.SSS.#SS#..S.SSS.S.SSS.SS..S#S.#.#SS.S#.SSS#S.SS.......#.#S#..#.S....#SS#S#.S..###..S.S#
                S.#.#....#S.SSS.SS###SSSS#..S#....S...SS...##S.SS.S..SSSS..#SS..S##..S...SS#.#.SS.##S..S..SSS.#.#.S.S
                .S..#.S.#...#SSS..##...#...SSS.#S#...#..####S....SS.SS.S.S..SS..S##.SS..S...#S##SS.SS..SS.S#.SSS....#
                ####SSS.S#.#.S#.S#.SS#.#SSSS.##.S..S##S..#...SS.#.#.S#...S#.#S.S##S.....SS.###S.#.#SSSS#SS..#S#S....#
                ..SSSSS.SS..S..SSS#.S#SS#....SS##.####SS...#.#S.#..#S#.S.SS.SSS##S..S#.SS..##.S.#.S..S#S....S..S.#.SS
                #SS.SSS.#S###.S.###S.S.#S.SS#.SS#S....S..#SS#.S#.#S#...#S.......#.#...#.SSS.#SS.#SS.SSS..SSS...S.S..#
                S...S....S..SS##.SS.S......#..#..#.#.S.SSS##......#S.#..S..#..##S#..S.S#..S..S#S.S..#S#S##.SS##...S.S
                ....#SSS.#SSS.SS..#..#S#...#.SS#.S.S..S.##SS.SS##S.SS..S.S..#..#..S....S#..S.SSSS.S###S...#SS.##SS#S.
                ..S#.S###SS..S..S#..#SS..S.S.SSSS.#SS.S#..SSSS.#S..SSS.SSS.SSS###S...SSSSS......#S.#S.#S..##..###S...
                #S#S.#.#S#S..SSSS##S....SS.SS..S.#..S.S..#S...#S.S..##S.#SSS#.#SS...S.S..#SS...SSS##.S###..SS###.#.##
                #..SS#.S.SSS..SSS.S...#SS.#S.##.#S#.S#S.S.S##S#..#.#.#.SS.S.###SS.SS#..#..##S#...#..#SS.S...#.S#...SS
                S##.S..S#.SS..#S.SS.S###..##S.#S.##.#..#S.S.S...S.S..S##S#.#SS.##.SSS#.S.##..##.#....S.S#.##...S.SSSS
                S.S#.##S#SS.#.S##SS..#..#S..#....S..SS.SS.S.S##.SSS###S#..SS...S#..#..S#.#.SSS...##...S..S.#.S#..#SS#
                S..SS#S...SS..#.S.S.S..#S......#...S.SS#.....S##S#.#.....S....#..##SSSSS##..S.SSSS##S.S.SS.....SSSS.#
                ..S..#SSS...SSSS..S..##.#..#S..S#.#..SS#...##S...#S.#S#.#.#S#S....##S.S..SS#S#.#SS##S##S#..#S.SS.SS##
                ....S#...#...#S...SS..S..#.S#.#..##S.SS.SSSSSS.#SSSSSS.#SS#S#.......S...#SSSS#S..S...S.S##SS.S#.SS.##
                ...S.S.##..SS#SS...S..S...S#...S...#.S#S###.SS#..S.S#..SS.#.S.SSSSS.SSS.#.S.SSS...S...S.#.#..SS.S.#S.
                S.#.S.#..S.##SSS.#S.##...SS#....#S.S#....S..##.SS.#.S..SS.#..#.#SS..#S#.S..SS#S..S..##S#.S#S.#SS#...S
                #.....#.##S#.#..S..###..SS..#S.#SS...SSS#SSS...S.##..S.S.S.#S.S..SSS....S.SS..#..S...#S.#.S.##..SS..S
                S.S.SS.S#S.#.SSSSSS#S.S###S.S..S.S.S#.....S...SS#S.S.S.S..SS.SSSSS##SS#SS.SS.#.#S##S.S#.S.SSSSS...SS.
                #S.SS...S.S#S#S..##....#S#SS.SSSS.S###S..S.S#....#....###.SS.S#...#S..S..S#..#.S#SS#S.SS....SS.#..##.
                #SS##S..S.....SS...###SSS..S#......S#####S.#S.S..SS#S##S#.S.#SS...SS..#S.##S.S##S....S...SS..##S#SS#.
                ...#.SS.######..###SS#.S.#.#.SS.##S...S.##..S.#S#.....SS...S#..S..S.#.S#SSS.....S#.S##.S.....#SS.#S..
                .SS..S#S....###S.S#..S.S...#.#SS.##...#SSSSSSSS#....SSS..#S..#S.#.S..#SS###.#..#.S#S.S#..#S..#.##..S#
                ####........SS.#..S.S......#SS##SSSS#.#.S.....S...#SS#.S..S..SS#..#SSS..S......S..##.SS.S...S.SS..SS#
                .SSS.S...#SS..S.S#....#SS.S#..#S....#SSS#..#......D##.S.S.#S..S#.S..SS.#.S.S.SS.S.##.###S.S#..#S#S.S#
                ...#..S.#S..##SS..#...SS.S.#S.S####S#S#S.#.S....S#S...S#.S..#...S.#SS#SSS#.S#S.SS#SSS..#.#..#.#SS.S..
                S.##.#.S..##.SS..SSS..SS.#SSSS.S.SS##..SS#..SS.....S##...#.##SS.S...##....#SSSS.##.S..S.S.SSS#SSS..##
                SSS.SSS#SS.#S#.S..S..#.SS.#.#S......S..SSSS.S..S.S....#S....SS..SSSS#SS.SS.S.SS.#.S.S...####.SS.S...S
                SS..SSS.S.SSSSSS.#.#S##...#.....#SS.S#..S......#S..#..#.###.....#SSS..##S.........SS..S#.#S.##SS.#SS.
                ..#.S.S#SSS.S.#SS#S...###.#S.SS.#S...#.#S.S..#SS..SS#S.SSS#.#.#S#..##.#.SS##.#S##S#S..S..#SS#..S..#SS
                #S.S.S.#.S#.SS#...S...SS..#S#S.#SSSS.......#.#..SS..#..#.S..S..SS.#.#.#..SS.S#..SSSS.S#.....S#S..SS##
                .#.S...S#S#SSSS.#.#.S..S##S#S..#...SS.S.##SSS##.####S##.....SS####..S#SS.#SS.#S#.#..S..S...#.###..#.S
                S..#SS#S#S.#..#.#....##...###.S..S#S.##.##SS.S....SS.#..S..#S#.#..S.......#S#.##S###SSS.#.SSSS##.S#..
                .#..S.S...#....S........#.SS#.#S.S...SS..#S..#..#....S..#..S#S...SS..#SS........#SS.#.....#SS..SS##SS
                .S..S.S.SSS.SS#S.S.SSS.S#.#S#..#..#...S.S.SSSS.#.S.SS.#S...S..S..S.##S.S.S..S#..S..#....S#.S#.S#.....
                ..#SS#S..S#.S.S##.#...S#..S.SSS#..S...#SSSSS.S.S...#.#S..#.#SSSS...#.SS.S...S.SS#S.SS..S#.S..#S.#.SS.
                SSS.S..S..S#...SS..###S.#.S#...SSS.S...#S.......S.....S..S...#S..S#SSSS.S.SS#.S.S.S.#..##......#..#S.
                S#S.S.#S.SS.S#S#SSS..SSS#.S....S#.S.S.#S.SSS...S..S..##SSS.#S...#S.#..##...S##S##.S###S.#.S#.#...S##S
                S....S..S.SSS...#.S..SS#..S##S.#SS#S.#.#S.SSS#S.SS.#.SSSSSSS.....#SS.#.S.#.S##S..##...#S.S#SS#.S#.#SS
                .SS.#S..##.###.....S..#..#.#.......#S..#S.S.#SS...S.S#S..#SS.S.S..S###S.SS.SS.#SS...##SSSS.S.S#....#S
                #SS.#...S.SS...S.##S#.SS..#...SSSS..S#SS.##S#SSSSS#SS.S#.S..#..SS#.#.#.#S......##.SS.#SS.#..#.#.S#...
                .S.#SS.S..#...#S.SS.SS.S#.SS..S..S#..#S..S....S#SS..###S#..#...S..##..#.S.#S#.#..SSS....S##S.#.SS##..
                #...SS###SS#SS.#.SSS.#S.#..S.SSS.......#.#...SSSSSSS.S.S..........#....S.S.#...S...S.S..S#S#..S.##S#.
                S.S.#S..#S..#.SS###.#.#..#SS..SS...SS.S..#.SS.S..#S...S.S.##.##.##...#.SSS.SS#S#.SS.S....S.SS.##...#S
                SS.SSS.#SS#.##.#.SS.S.S.SSSSSS#S#SSS...###.S.S..S...SSS.#SS.S..#S.#..#.S...SS.#S..S.##S.#....#S#...SS
                SSS#S.SS.SS.S..S..SS....#.SSS..S..S.SSSS...#S..S.S...##.#S#.#..SSS.##SS###....S.SS.S##.S.#.SS#.......
                ...S..#.SSSS.S..SS#S.S.S#S.#..S.#.#S#...#.....#..S#...S.S..##S##S#.S.#.#S#.S...S.S.#.#.SSS......##SS.
                ..SS.S.SS##..S.###S#..S.S##.S..##.SSS......#.SSSS..#.#.SSS..#.........S#...##SS#.SS##SSS##..SSSSSS.#S
                S#S.S..S.#.S....S.SS.##.SS#..#S.#.SS..SSS.SSS..SS.#.S..S#.S.##S#.#S.....S.S..S###SS.S.S.S.S#SSSSSS.SS
                ..SSSS...###SS.S.SS.SS....S..##....##.SSS.S.S##S.#S.SS..#..#.S#...S.S#.###....S##S#S.###.S###...S#...
                #SS.SSS..#SSSS........#.###.##S...S#.SSS#..#.S#S#S...#SS#.S#..S#S###...SS#.S..S......S#..S.#..#S#.S..
                #..S#.SS..S##S.#.#S...##.#..SS.#.S#S#SS##.#S..#.....#.S.S.S.SS..SS##S.S#...##..#S#....#SSS#.S#.##SS..
                ...S.S.....S.#...S.S....S...#..S..SSSS#....S.SS.#.#S#.S...#.#.S.#.SS#.#..S#S#.S#..SS.##S.#SS.S#.S.S.S
                SSSSS.#SSS#...S#..S.#.#.S.S#.S#S#S.S.##...S#S.S#S#...S.SSS..#...SSS.##.SSSSS.S..#S..S#..SS.S.S.#S##.#
                ..S.S#.S#...#SS...S.S..SS....S..SS.##S.SS.#SS.##.S#.#...S.S#...S..#.S.S....S#S#S#.SS.SS....#S#S.S#.##
                S..S...SSSS..SS....SS.S..#.#S#.SSS#.#.SS#S##S..S#S.S#S#S#SS.SS.##S.#.#.#S.S##.....S.S.SS....#.##SS...
                ...##SS#.SS#.S.SS..#.#..#S.S.SS..##..#..#.S...S.#.SS...S#.#S#..###.#.S.S.SS...#S.......#SS.S#..S..S#S
                #S..#S#SS#.S.SS#S...SS...S#S.S..SSS.S.SSSS..##S#..#.S.SS.#S#..S...#S#.SS...#.S#.S.S#..#..S.SS#.S.S..S
                S.S.S.SS.#S#SS#SS.###...#.#......S#.S#SS....S#.#.S...S...S.S#.S.#..#.SSS..#.S##.##.###S.#.#..S#S.#.##
                #..#....#.S...#.SSS..#.S..#.S.....#...S.SSS##..S...##.#S#S#.##..#....S.#S#S#S#S...#.#.S...S##SSS..S.#
                .S##SSS......#.S..#.S..S..##.SS#...S.........S#.S...#S.S.S..S#.SSSS.SS.#.....SS..S#..S#S#S###S#S#....
                #S.S.##.SSS.S.#.SS.S.#.#S.S..##..SS...S..#S.#.S#S.##S.SSS..S..##...S.S...SS##..#.#S.S...S.S#.#.SS.#..
                #S#..SSS#..S..SSS##..S.SS#S.SSS#SSS.SS..S.#..#.#.S#.SSS..##S..S.S..SSSS#.S#S....#.S.S##..S..#SSS.#.#.
                SS...#S#..SS.S.SSSS..##S#S.S.S..#..#.S#SS#...SS.S..#S##.S.S#.###S#...#.S#.S.S.SSS#SS...S.S##..SSSS.SS
                #...#.S.S....#SS##..SS#.#SS.#.S.#..#SS#S.SS.S...#..#S#.#S#S.S.SS.SS#SS..S.#...S.#..SS.SS...S..#.S##..
                #S.S.SSSS.##S#S#..#SS.#S#S#.S..#...S.S#S.#S...S.S.S.SSS#SS.S.S.#S.##S.S....SSSS..S.S.##..#.#...#...S.
                #S#S##S#...S....####..#.S....S.S.S...SSSS##.##S#.S.#S.#SSS#S.S#S.S.S..SSS#.#S.#..#.#.#..#S#...S#...#.
                S.S#S.S#...S.S..S...S.S...S.S..#..SSSSS.S.S#.S..S.#..#..S.S.SSS.S.SSS##S#S.#.SS##..SS..###.S.S.S.#.SS
                S#...S..S.SS.S#.SS#.##S..SSSS.#SS#S.S..#SS#.S#S..S.###......S..#S....#.#..#SS...SSS..#......SS..S.S.S
                ..SS#S###S##..S.S...SS.S.S...SS.#S...S.S...#S.#S..S#.SS.#S...SSS#S#S#.#.S#.S.S#...S.SSS..#..S#.#S.S..
                SSS..SS.#.S..SS#SS.SSS.S.S.#S#..##S....SS.S#S..#.S##S.SS.#.S#S.S##SS##.S.#S....SS.#..#....#...S.###..
                S.S....S#S#..S.S..SSS#S.#.S#SS.#.S#......S..S##SS.SS#.S.##.S.S....S.#S...SS.##..#.S.SS....###.SSS..S#
                #.#S#.#..S#S#..SS...SS#.S.####.#S#SSS.S##.SS...SS.SSSS.#....S.#S.#.SS..#.S.S.#S#.SS.S.S#.SS#S#S.SSSS.
                #SS#S.#SS#.##.SSSSS..S#SS#.S#SS#..#.SSSS.##...S.S...S....#S..S#SS.S...S...S......S#.SS.S.S#SSS.#.#.SS
                ..SS.......S.#SSS.S.SSS#S..#.#.#..S.S.##...SS...S.#SS#.#.#####S.#.SS.S..##S.#...#..S.......S.#S#.S..#
            """.trimIndent().lines().map { it.toList() }

            val TOTAL_MOVES = 20

            var dragons = mutableSetOf<Pair<Int, Int>>()
            var sheep = mutableSetOf<Pair<Int, Int>>()
            val hideouts = mutableSetOf<Pair<Int, Int>>()
            val graph = buildGraph(
                input, ::Node,
                callbacks = mapOf(
                    'D' to { _, n -> dragons.add(n.y to n.x) },
                    '#' to { _, n -> hideouts.add(n.y to n.x) },
                    'S' to { _, n -> sheep.add(n.y to n.x) })
            )

            var moves = 0
            var eatenSheep = 0
            while (moves < TOTAL_MOVES) {
                dragons = dragons.flatMap { next ->
                    listOf(
                        2 to 1, 2 to -1, -2 to 1, -2 to -1,
                        1 to 2, 1 to -2, -1 to 2, -1 to -2
                    ).map { (dy, dx) ->
                        next.first + dy to next.second + dx
                    }.filter {
                        graph.contains(it)
                    }
                }.toMutableSet()

                var splitSheep = sheep.groupBy { it in dragons && it !in hideouts }
                eatenSheep += splitSheep[true]?.size ?: 0
                sheep = splitSheep[false]?.toMutableSet() ?: mutableSetOf()

                sheep = sheep.map { it.first + 1 to it.second }
                    .filter { graph.contains(it) }
                    .toMutableSet()

                splitSheep = sheep.groupBy { it in dragons && it !in hideouts }
                eatenSheep += splitSheep[true]?.size ?: 0
                sheep = splitSheep[false]?.toMutableSet() ?: mutableSetOf()

                moves++
            }

            println(eatenSheep)
        }

        fun part3() {
            val input = """
                SS.SSS.
                .......
                ##.....
                #..###.
                #####.#
                ###D###
            """.trimIndent().lines().map { it.toList() }

            var dragons = mutableSetOf<Pair<Int, Int>>()
            var sheep = mutableSetOf<Pair<Int, Int>>()
            val hideouts = mutableSetOf<Pair<Int, Int>>()
            val graph = buildGraph(
                input, ::Node,
                callbacks = mapOf(
                    'D' to { _, n -> dragons.add(n.y to n.x) },
                    '#' to { _, n -> hideouts.add(n.y to n.x) },
                    'S' to { _, n -> sheep.add(Pair(n.y, n.x)) })
            )

//            val moves = mutableListOf<String>()

            fun pointToChess(point: Pair<Int, Int>): String {
                val file = 'A' + point.second
                val rank = 1 + point.first
                return "$file$rank"
            }

            var sheepMoves: MemoizedFunction<Pair<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>, Long>? = null
            val dragonMoves = memoize<Pair<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>, Long> { (dragon, sheep) ->
                listOf(
                    2 to 1, 2 to -1, -2 to 1, -2 to -1,
                    1 to 2, 1 to -2, -1 to 2, -1 to -2
                ).map { (dy, dx) ->
                    dragon.first + dy to dragon.second + dx
                }.filter {
                    graph.contains(it)
                }.sumOf { newDragonPos ->
                    val newSheep = sheep.filter { it != newDragonPos || newDragonPos in hideouts }.toMutableSet()
                    if (newSheep.isEmpty()) {
//                        println(moves.joinToString(" ") + " D>${pointToChess(newDragonPos)}")
                        1L
                    } else {
//                        moves.add("D>${pointToChess(newDragonPos)}")
                        val retVal = sheepMoves!!(newDragonPos to newSheep)
//                        moves.removeLast()
                        retVal
                    }
                }
            }
            sheepMoves = memoize { (dragon, sheep) ->
                var sheepSum = 0L
                val invalidMoves = { sheep: Pair<Int, Int> ->
                    (sheep.first + 1 to sheep.second == dragon &&
                            sheep.first + 1 to sheep.second !in hideouts) ||
                            (sheep.first + 1 to sheep.second !in graph)
                }
                if (2 to 0 in sheep || 3 to 4 in sheep) {
                    0L
                } else if (sheep.all { invalidMoves(it) }) {
                    if (sheep.any {it.first + 1 to it.second !in graph }) {
                        0L
                    } else {
                        dragonMoves(dragon to sheep)
                    }
                } else {
                    sheep
                        .filterNot { invalidMoves(it) }
                        .forEach { s ->
                            val temp = s.first + 1 to s.second
                            val newSheep = sheep.filter { it != s }.toMutableSet()
                            newSheep.add(temp)
//                            moves.add("S>${pointToChess(temp)}")
                            val retVal = dragonMoves(dragon to newSheep)
//                            moves.removeLast()
                            sheepSum += retVal
                        }
                    sheepSum
                }
            }

            println(sheepMoves(dragons.first() to sheep))
        }
    }
}

fun main() {
    Quest10.part1()
    Quest10.part2()
    Quest10.part3()
}
//
//S>A2 D>C3 S>A3 D>A2 S>A4 D>C1 S>A5 D>B3 S>B2 D>A5 S>B3 D>B3
//S>A2 D>C3 S>A3 D>A2 S>A4 D>C1 S>B2 D>B3 S>A5 D>A5 S>B3 D>B3  Y
//S>A2 D>C3 S>A3 D>A2 S>B2 D>C1 S>A4 D>B3 S>A5 D>A5 S>B3 D>B3  Y
//S>A2 D>C3 S>A3 D>A2 S>B2 D>C1 S>B3 D>B3 S>A4 D>A5 D>B3 S>A5 D>A5  Y
//S>A2 D>C3 S>A3 D>A2 S>B2 D>C1 S>B3 D>B3 S>A4 D>A5 D>C4 S>A5 D>A5  Y
//S>A2 D>C3 S>A3 D>A2 S>A4 D>C1 S>A5 D>B3 S>B2 D>A5 S>B3 D>C4 S>B4 D>A3 S>B5 D>B5
//S>A2 D>C3 S>A3 D>A2 S>A4 D>C1 S>B2 D>B3 S>A5 D>A5 S>B3 D>C4 S>B4 D>A3 S>B5 D>B5  Y
//S>A2 D>C3 S>A3 D>A2 S>B2 D>C1 S>A4 D>B3 S>A5 D>A5 S>B3 D>C4 S>B4 D>A3 S>B5 D>B5  Y