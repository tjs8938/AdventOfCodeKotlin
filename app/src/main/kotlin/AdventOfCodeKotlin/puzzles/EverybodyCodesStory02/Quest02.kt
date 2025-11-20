package AdventOfCodeKotlin.puzzles.EverybodyCodesStory02

import AdventOfCodeKotlin.util.ModularArithmetic

fun main(args: Array<String>) {
    Quest02.part1()
    Quest02.part2()
    Quest02.part3()
}

class Quest02 {

    companion object {
        fun part1() {
            val input = """
                GBRRBBGGRBRBRGRRRBBGBBBGGGRBBRBRRGBGGBRRRRRGRGBBBGRRGBRBRRRRRBRGGRRRGGRGBBGGRGRGGRRGBBBRRBBBBBRGRBRGGGRRBBGBGBRRRRRRRRRRGGGGGGGGGGBBBBBBBBBBGBRGBRGBRGBRGBRGBRGBRGBRGBRGBRGBRGBRGBRGBRGBRGBRGBRGBRGBRGBR
            """.trimIndent()

            var boltCount = 0
            var index = 0
            val boltList = listOf('R', 'G', 'B')
            while (index < input.length) {
                val bolt = boltList[boltCount % 3]
                boltCount++

                while (index < input.length && input[index] == bolt) {
                    index++
                }
                if (index < input.length) {
                    index++
                }
            }
            println(boltCount)

        }

        fun part2() {
            val input =
                "RBBGGGGRGRGGRRGBRRBRBBBGBRBGGRRRBBGGGRGGBRGGRGGRGGGRBBGRRRGRBBGRRRRGRRRGBRGRRGBGRBRGRGGBGGGBGRBRGGGGGBBGGGRRRGBBBRRBBGGGBBRRGBRGBBBBBBGGBRBBRBGRBRRBGRRBBRGBBGRGRRGRRBRBBGRGGGRRGRRRGGGBGGGGBGGGGBGBBBGBGGBGGBBRRRGBGGGBBGGBGGBBBRGGRBRGGGGRRBBBGGRGBRRGRBGGGBGG"
            val repeatCount = 100
            var boltCount = circlePop(input, repeatCount)

            println(boltCount)
        }

        private fun circlePop(input: String, repeatCount: Int): Int {
            var boltCount = 0
            var topIndex = 0
            var remainingBalloons = input.length * repeatCount
            var middleIndex = remainingBalloons / 2
            val poppedFromMiddle = mutableSetOf<Int>()
            val boltList = listOf('R', 'G', 'B')
            while (remainingBalloons > 0) {

                val bolt = boltList[boltCount % 3]
        //                println("bolt: $bolt, topIndex: $topIndex, topColor: ${input[topIndex % input.length]}, remainingBalloons: $remainingBalloons")

                boltCount++

                if (remainingBalloons % 2 == 0) {
                    if (input[topIndex % input.length] == bolt) {
        //                        println("Pop the midpoint")
                        remainingBalloons--
                        poppedFromMiddle.add(middleIndex)
                    }
                    middleIndex++
                }

                remainingBalloons--
                while (++topIndex in poppedFromMiddle) {
                    poppedFromMiddle.remove(topIndex)
                }
            }
            return boltCount
        }

        fun part3() {
            val input = "BRBBBGGGGBBBGGBRRRGBBGBGRRGRRGRRRRRBBBRBRBGBRRBBRRRBRGRBRBBGBRRGGBRRGRGGGGGGGGGBRRBRBBRRBGBBBRGGBGGBBGRRBBRBBRBGGBBBGBBRBGRRRBBGGRRGRGRBRRBRRRRGBGGBRGBBGGBGGBBRGGRGBRBBBBBGBBBRRRBGRRBRGRRBGGBBBGGRRRRGRRRGRRRGRBBBGRBBGBGRBRGRRBRRGBRBBGBRBGBGBRRGGGGGBRBRGRGG"
            val repeatCount = 100000
            println(circlePop(input, repeatCount))
        }
    }
}
