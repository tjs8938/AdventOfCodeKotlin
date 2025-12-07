package AdventOfCodeKotlin.puzzles.EverybodyCodes2025

class Quest14 {
    companion object {
        fun part1() {
            val input = """
                ##...#.#.#
                .####.#.#.
                ###.##...#
                ........##
                ..#.##..##
                ....#..##.
                ##.......#
                #......#.#
                #######..#
                ..#.##...#
            """.trimIndent()
            var tiles = mutableMapOf<Pair<Int, Int>, Boolean>()
            input.lines().forEachIndexed { y, line ->
                line.forEachIndexed { x, char ->
                    tiles[Pair(y, x)] = char == '#'
                }
            }

            val rounds = 10
            var total = 0
            repeat(rounds) {
                tiles = tiles.map { (loc, state) ->
                    val activeDiagonals = listOf(
                        Pair(loc.first - 1, loc.second - 1),
                        Pair(loc.first - 1, loc.second + 1),
                        Pair(loc.first + 1, loc.second - 1),
                        Pair(loc.first + 1, loc.second + 1)
                    ).count { tiles.getOrDefault(it, false) }
                    val newState = (state && activeDiagonals % 2 == 1) || (!state && activeDiagonals % 2 == 0)
                    Pair(loc, newState)
                }.toMap().toMutableMap()
                total += tiles.count { it.value }
            }
            println(total)
        }
        fun part2() {
            val input = """
                ..##..##.##...#..#.#.#..#.###.#.##
                .##..#...#.##...####..#..#..#..##.
                ....##.#.#.##.#.##..#.#....####.#.
                .#.##.#..###...#.##..#.#...#..#.#.
                ..#.##.##.#.#...##...##.#.......##
                .##...#.##.#...#.#..##...##...#...
                #..#####....#.#..###########.#..##
                #.#.#..##...###.#..#.##.#..#.##.##
                ....#....#..#.#..#.....##.#.....#.
                #...#.#.#.###....##.#.#.##.###.##.
                #.##.##..#########..#..###....##.#
                ##.##...#..#.###.#.......#..######
                .####.#.#...##.##.####..##...#.###
                ..#..#.#...#.##..#..#.#..###.###..
                .#####.##.#..#.#.##..#...####.##..
                .##.#.#..#.#####.###.#.###.....#..
                .....###.##.#...#..##.#.##........
                #..#.....#.##..#.#...#....###.##.#
                .#...###.####..#.#..#####.##.#.#..
                .#...####..##.#..#.##.#.##.#.##...
                .#..##..#.....#..#..###.#.###..#..
                .#.###..#..##...##.####.###....###
                .##.##..#####.#.........#..#.#.#.#
                ###..##..###..##..###...####...##.
                .........#.#.##..#..######.#.#..#.
                ##.....#..###.#.##.####.#.....#.#.
                .#.#.##########.#.##..#.#...####..
                .#.#..#.#.#..##.......#####..####.
                #.##...#.#####..##.#.##..#.#.....#
                .#.##.####....#..####.##.##.#.#.#.
                #.#.#.##.##..#......###...#..#.#.#
                .###.#..##.#..###.##.##..#.##..###
                ##...#.#..#.#.#..#.###.....##...##
                .###.#.#.#.##......##.##..#.#..#..
            """.trimIndent()
            var tiles = mutableMapOf<Pair<Int, Int>, Boolean>()
            input.lines().forEachIndexed { y, line ->
                line.forEachIndexed { x, char ->
                    tiles[Pair(y, x)] = char == '#'
                }
            }

            val rounds = 2025
            var total = 0
            repeat(rounds) {
                tiles = tiles.map { (loc, state) ->
                    val activeDiagonals = listOf(
                        Pair(loc.first - 1, loc.second - 1),
                        Pair(loc.first - 1, loc.second + 1),
                        Pair(loc.first + 1, loc.second - 1),
                        Pair(loc.first + 1, loc.second + 1)
                    ).count { tiles.getOrDefault(it, false) }
                    val newState = (state && activeDiagonals % 2 == 1) || (!state && activeDiagonals % 2 == 0)
                    Pair(loc, newState)
                }.toMap().toMutableMap()
                total += tiles.count { it.value }
            }
            println(total)
        }
        fun part3() {

            val input = """
                .#....#.
                ##....##
                ...##...
                ..####..
                ..####..
                ...##...
                ##....##
                .#....#.
            """.trimIndent()

            val targetTiles = mutableMapOf<Pair<Int, Int>, Boolean>()
            input.lines().forEachIndexed { y, line ->
                line.forEachIndexed { x, char ->
                    targetTiles[Pair(y + 13, x + 13)] = char == '#'
                }
            }

            var tiles = mutableMapOf<Pair<Int, Int>, Boolean>()
            (0 until 34).map {y ->
                (0 until 34).map { x ->
                    tiles[Pair(y, x)] = false
                }
            }

            fun printTiles(tiles: Map<Pair<Int, Int>, Boolean>) {
                for (y in 0 until 34) {
                    for (x in 0 until 34) {
                        if (tiles.getOrDefault(Pair(y, x), false)) {
                            print("#")
                        } else {
                            print(".")
                        }
                    }
                    println()
                }
                println()
            }

            var loops = 0L
            val foundPattern = mutableListOf<Pair<Long, Long>>()
            while (true) {
//                printTiles(tiles)
                tiles = tiles.map { (loc, state) ->
                    val activeDiagonals = listOf(
                        Pair(loc.first - 1, loc.second - 1),
                        Pair(loc.first - 1, loc.second + 1),
                        Pair(loc.first + 1, loc.second - 1),
                        Pair(loc.first + 1, loc.second + 1)
                    ).count { tiles.getOrDefault(it, false) }
                    val newState = (state && activeDiagonals % 2 == 1) || (!state && activeDiagonals % 2 == 0)
                    Pair(loc, newState)
                }.toMap().toMutableMap()

                loops++

                if (targetTiles.all { tiles.getOrDefault(it.key, false) == it.value }) {
                    if (foundPattern.isNotEmpty() && loops - foundPattern.last().first in foundPattern.zipWithNext { a, b -> b.first - a.first }) {
                        break
                    }
                    foundPattern.add(loops to tiles.count { it.value }.toLong())
                }
            }
            val loopCounts = foundPattern.map { it.first }
            val tileCounts = foundPattern.map { it.second }
            val fullRounds = (1000000000L - loopCounts.first()) / (loopCounts.last() - loopCounts.first())
            var remainingLoops = (1000000000L - loopCounts.first()) % (loopCounts.last() - loopCounts.first())
            loops = fullRounds * tileCounts.drop(1).sum()
            loopCounts.zipWithNext().map { (a, b) -> b - a }.forEachIndexed { index, value ->
                if (remainingLoops >= value) {
                    remainingLoops -= value
                    loops += tileCounts[index + 1]
                } else {
                    remainingLoops = 0
                    return@forEachIndexed
                }
            }
            loops += tileCounts[0]
            println(loops)
        }
    }
}

fun main(args: Array<String>) {
    Quest14.part1()
    Quest14.part2()
    Quest14.part3()
}
