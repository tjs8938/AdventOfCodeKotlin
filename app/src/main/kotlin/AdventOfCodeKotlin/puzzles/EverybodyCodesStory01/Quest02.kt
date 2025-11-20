package AdventOfCodeKotlin.puzzles.EverybodyCodesStory01

import AdventOfCodeKotlin.util.ModularArithmetic

fun main(args: Array<String>) {
    Quest02.part1()
    Quest02.part2()
    Quest02.part3()
}

class Quest02 {

    class BST<T : Comparable<T>> {
        data class Node<T : Comparable<T>>(
            val value: T,
            var left: Node<T>? = null,
            var right: Node<T>? = null,
            var parent: Node<T>? = null
        )

        var root: Node<T>? = null
        val nodesById = mutableMapOf<Int, Node<T>>()

        fun insert(id: Int, value: T) {
            root = insertRec(id, root, value)
        }

        private fun insertRec(id: Int, node: Node<T>?, value: T): Node<T> {
            if (node == null) {
                nodesById[id] = Node(value)
                return nodesById[id]!!
            }
            if (value < node.value) {
                node.left = insertRec(id, node.left, value)
                node.left?.parent = node
            } else if (value > node.value) {
                node.right = insertRec(id, node.right, value)
                node.right?.parent = node
            }
            return node
        }

        fun traverse(action: (T, Int) -> Unit) {
            traverseRec(root, 0, action)
        }

        private fun traverseRec(node: Node<T>?, depth: Int, action: (T, Int) -> Unit) {
            if (node != null) {
                action(node.value, depth)
                traverseRec(node.left, depth + 1, action)
                traverseRec(node.right, depth + 1, action)
            }
        }
    }

    companion object {
        fun part1() {
            val input = """
                ADD id=1 left=[215,H] right=[161,Z]
                ADD id=2 left=[269,X] right=[103,W]
                ADD id=3 left=[111,P] right=[137,Z]
                ADD id=4 left=[285,J] right=[116,Z]
                ADD id=5 left=[250,B] right=[261,R]
                ADD id=6 left=[143,M] right=[204,S]
                ADD id=7 left=[108,X] right=[271,H]
                ADD id=8 left=[105,Q] right=[256,J]
                ADD id=9 left=[295,N] right=[123,P]
                ADD id=10 left=[260,!] right=[104,X]
                ADD id=11 left=[102,M] right=[148,B]
                ADD id=12 left=[110,U] right=[119,G]
                ADD id=13 left=[113,A] right=[252,F]
                ADD id=14 left=[200,C] right=[207,X]
                ADD id=15 left=[281,T] right=[263,F]
                ADD id=16 left=[238,K] right=[156,S]
                ADD id=17 left=[139,Y] right=[197,P]
                ADD id=18 left=[251,N] right=[262,G]
                ADD id=19 left=[193,G] right=[121,Y]
                ADD id=20 left=[153,L] right=[270,V]
            """.trimIndent()

            process(input)
        }

        private fun process(input: String) {
            data class Node(var rank: Int, var symbol: Char) : Comparable<Node> {
                override fun compareTo(other: Node): Int {
                    return this.rank - other.rank
                }
            }

            val leftTree: BST<Node> = BST()
            val rightTree: BST<Node> = BST()

            input.lines().forEach { line ->
                val regex = Regex("""ADD id=(\d+) left=\[(\d+),(.)\] right=\[(\d+),(.)\]""")
                val matchResult = regex.matchEntire(line)
                if (matchResult != null) {
                    val (id, leftRank, leftSymbol, rightRank, rightSymbol) = matchResult.destructured
                    leftTree.insert(id.toInt(), Node(leftRank.toInt(), leftSymbol.single()))
                    rightTree.insert(id.toInt(), Node(rightRank.toInt(), rightSymbol.single()))
                } else {
                    val swapRegex = Regex("""SWAP (\d+)""")
                    val swapMatch = swapRegex.matchEntire(line)
                    if (swapMatch != null) {
                        val (id) = swapMatch.destructured
                        val leftNode = leftTree.nodesById[id.toInt()]
                        val rightNode = rightTree.nodesById[id.toInt()]
                        if (leftNode != null && rightNode != null) {
                            val tempSymbol = leftNode.value.symbol
                            leftNode.value.symbol = rightNode.value.symbol
                            rightNode.value.symbol = tempSymbol

                            val tempRank = leftNode.value.rank
                            leftNode.value.rank = rightNode.value.rank
                            rightNode.value.rank = tempRank
                        }
                    }
                }
            }

            val leftMessages: MutableList<MutableList<Char>> = mutableListOf()
            val rightMessages: MutableList<MutableList<Char>> = mutableListOf()
            val leftTreeAction: (Node, Int) -> Unit = { symbol: Node, depth: Int ->
                if (depth >= leftMessages.size) {
                    leftMessages.add(mutableListOf())
                }
                leftMessages[depth].add(symbol.symbol)
            }
            val rightTreeAction: (Node, Int) -> Unit = { symbol: Node, depth: Int ->
                if (depth >= rightMessages.size) {
                    rightMessages.add(mutableListOf())
                }
                rightMessages[depth].add(symbol.symbol)
            }
            leftTree.traverse(leftTreeAction)
            rightTree.traverse(rightTreeAction)
            println(leftMessages.maxBy { it.size }.joinToString(separator = "") + rightMessages.maxBy { it.size }
                .joinToString(separator = ""))
        }

        fun part2() {
            val input = """
                ADD id=1 left=[861,T] right=[967,R]
                ADD id=2 left=[897,Y] right=[857,B]
                ADD id=3 left=[887,F] right=[853,G]
                ADD id=4 left=[854,N] right=[788,T]
                ADD id=5 left=[841,R] right=[894,W]
                SWAP 3
                ADD id=6 left=[806,R] right=[818,B]
                ADD id=7 left=[892,Y] right=[849,X]
                ADD id=8 left=[847,Z] right=[785,Y]
                ADD id=9 left=[820,B] right=[842,X]
                ADD id=10 left=[799,R] right=[834,V]
                SWAP 2
                ADD id=11 left=[793,X] right=[817,N]
                ADD id=12 left=[859,T] right=[882,L]
                ADD id=13 left=[883,W] right=[889,J]
                ADD id=14 left=[876,V] right=[790,J]
                ADD id=15 left=[827,G] right=[822,B]
                SWAP 7
                ADD id=16 left=[843,Z] right=[791,S]
                ADD id=17 left=[879,W] right=[862,H]
                ADD id=18 left=[237,R] right=[278,Z]
                ADD id=19 left=[235,Y] right=[270,W]
                ADD id=20 left=[220,G] right=[229,F]
                SWAP 1
                SWAP 4
                ADD id=21 left=[828,F] right=[250,V]
                ADD id=22 left=[186,Q] right=[271,M]
                ADD id=23 left=[174,R] right=[301,Y]
                ADD id=24 left=[888,L] right=[811,G]
                ADD id=25 left=[895,G] right=[896,N]
                SWAP 4
                SWAP 6
                ADD id=26 left=[800,W] right=[206,B]
                ADD id=27 left=[931,Y] right=[126,V]
                ADD id=28 left=[948,M] right=[193,V]
                ADD id=29 left=[816,S] right=[913,W]
                ADD id=30 left=[890,W] right=[152,G]
                SWAP 2
                SWAP 26
                SWAP 11
                ADD id=31 left=[899,V] right=[878,T]
                ADD id=32 left=[823,X] right=[256,L]
                ADD id=33 left=[937,W] right=[211,T]
                ADD id=34 left=[792,T] right=[228,N]
                ADD id=35 left=[223,N] right=[236,U]
                SWAP 10
                SWAP 12
                SWAP 22
                ADD id=36 left=[323,V] right=[116,Y]
                ADD id=37 left=[162,G] right=[289,J]
                ADD id=38 left=[499,C] right=[802,P]
                ADD id=39 left=[839,V] right=[461,R]
                ADD id=40 left=[835,Y] right=[444,T]
                SWAP 3
                SWAP 4
                SWAP 5
                SWAP 23
                ADD id=41 left=[443,G] right=[885,N]
                ADD id=42 left=[819,P] right=[856,N]
                ADD id=43 left=[158,H] right=[183,G]
                ADD id=44 left=[145,V] right=[165,P]
                ADD id=45 left=[699,Z] right=[320,S]
                SWAP 13
                SWAP 6
                SWAP 43
                SWAP 25
                ADD id=46 left=[482,V] right=[157,G]
                ADD id=47 left=[401,Z] right=[766,G]
                ADD id=48 left=[480,W] right=[242,M]
                ADD id=49 left=[299,A] right=[613,H]
                ADD id=50 left=[491,W] right=[341,V]
                SWAP 32
                SWAP 38
                SWAP 40
                SWAP 5
                SWAP 49
                ADD id=51 left=[906,T] right=[898,X]
                ADD id=52 left=[746,L] right=[345,X]
                ADD id=53 left=[137,S] right=[470,F]
                ADD id=54 left=[379,W] right=[243,N]
                ADD id=55 left=[322,V] right=[324,P]
                SWAP 34
                SWAP 4
                SWAP 28
                SWAP 6
                SWAP 39
                ADD id=56 left=[115,G] right=[244,T]
                ADD id=57 left=[446,S] right=[209,Z]
                ADD id=58 left=[121,M] right=[735,J]
                ADD id=59 left=[625,!] right=[921,B]
                ADD id=60 left=[432,K] right=[852,M]
                SWAP 11
                SWAP 7
                SWAP 22
                SWAP 49
                SWAP 34
                SWAP 28
                ADD id=61 left=[185,G] right=[750,P]
                ADD id=62 left=[556,J] right=[199,B]
                ADD id=63 left=[760,P] right=[768,W]
                ADD id=64 left=[309,S] right=[464,W]
                ADD id=65 left=[655,B] right=[363,R]
                SWAP 64
                SWAP 10
                SWAP 33
                SWAP 18
                SWAP 53
                SWAP 56
                ADD id=66 left=[676,Z] right=[365,H]
                ADD id=67 left=[781,P] right=[263,V]
                ADD id=68 left=[696,W] right=[810,T]
                ADD id=69 left=[385,S] right=[288,N]
                ADD id=70 left=[140,R] right=[226,S]
                SWAP 54
                SWAP 7
                SWAP 3
                SWAP 40
                SWAP 23
                SWAP 17
                SWAP 21
                ADD id=71 left=[267,S] right=[946,V]
                ADD id=72 left=[393,M] right=[214,S]
                ADD id=73 left=[916,B] right=[689,H]
                ADD id=74 left=[779,W] right=[557,T]
                ADD id=75 left=[778,W] right=[870,R]
                SWAP 61
                SWAP 66
                SWAP 53
                SWAP 63
                SWAP 65
                SWAP 29
                SWAP 15
                ADD id=76 left=[433,P] right=[337,M]
                ADD id=77 left=[599,B] right=[451,H]
                ADD id=78 left=[877,S] right=[198,B]
                ADD id=79 left=[960,X] right=[675,H]
                ADD id=80 left=[907,W] right=[398,Z]
                SWAP 15
                SWAP 23
                SWAP 8
                SWAP 74
                SWAP 4
                SWAP 56
                SWAP 47
                SWAP 69
                ADD id=81 left=[949,W] right=[142,P]
                ADD id=82 left=[754,S] right=[542,Z]
                ADD id=83 left=[221,R] right=[915,G]
                ADD id=84 left=[173,R] right=[932,G]
                ADD id=85 left=[564,X] right=[285,B]
                SWAP 53
                SWAP 40
                SWAP 25
                SWAP 68
                SWAP 24
                SWAP 58
                SWAP 55
                SWAP 4
                ADD id=86 left=[701,M] right=[624,J]
                ADD id=87 left=[109,T] right=[366,V]
                ADD id=88 left=[925,S] right=[380,J]
                ADD id=89 left=[294,P] right=[486,Z]
                ADD id=90 left=[688,P] right=[900,S]
                SWAP 64
                SWAP 81
                SWAP 11
                SWAP 9
                SWAP 73
                SWAP 16
                SWAP 12
                SWAP 22
                SWAP 44
                ADD id=91 left=[904,S] right=[805,V]
                ADD id=92 left=[574,R] right=[222,P]
                ADD id=93 left=[403,B] right=[344,X]
                ADD id=94 left=[353,L] right=[134,G]
                ADD id=95 left=[166,P] right=[759,F]
                SWAP 55
                SWAP 36
                SWAP 29
                SWAP 73
                SWAP 50
                SWAP 45
                SWAP 38
                SWAP 8
                SWAP 76
                ADD id=96 left=[686,M] right=[445,X]
                ADD id=97 left=[958,R] right=[680,X]
                ADD id=98 left=[864,Z] right=[681,P]
                ADD id=99 left=[438,P] right=[982,Y]
                ADD id=100 left=[607,H] right=[260,V]
                SWAP 79
                SWAP 5
                SWAP 77
                SWAP 32
                SWAP 6
                SWAP 21
                SWAP 22
                SWAP 35
                SWAP 78
                SWAP 41
            """.trimIndent()
            process(input)
        }

        fun part3() {
            val input = """
                ADD id=1 left=[735,X] right=[236,B]
                ADD id=2 left=[389,Y] right=[226,B]
                ADD id=3 left=[123,L] right=[185,B]
                ADD id=4 left=[505,S] right=[168,W]
                ADD id=5 left=[404,Z] right=[199,F]
                SWAP 2
                ADD id=6 left=[208,P] right=[195,S]
                ADD id=7 left=[494,X] right=[221,Z]
                ADD id=8 left=[405,H] right=[442,G]
                ADD id=9 left=[496,H] right=[103,N]
                ADD id=10 left=[461,W] right=[146,F]
                SWAP 9
                ADD id=11 left=[244,J] right=[170,G]
                ADD id=12 left=[399,V] right=[344,G]
                ADD id=13 left=[462,N] right=[209,F]
                ADD id=14 left=[407,X] right=[252,T]
                ADD id=15 left=[249,S] right=[214,P]
                SWAP 8
                ADD id=16 left=[490,S] right=[130,F]
                ADD id=17 left=[223,J] right=[523,R]
                ADD id=18 left=[377,B] right=[157,F]
                ADD id=19 left=[121,V] right=[213,L]
                ADD id=20 left=[207,J] right=[343,V]
                SWAP 10
                SWAP 17
                ADD id=21 left=[264,N] right=[246,V]
                ADD id=22 left=[443,X] right=[408,G]
                ADD id=23 left=[453,!] right=[550,U]
                ADD id=24 left=[233,F] right=[467,H]
                ADD id=25 left=[497,S] right=[172,G]
                SWAP 11
                SWAP 24
                ADD id=26 left=[538,V] right=[242,Q]
                ADD id=27 left=[456,F] right=[238,G]
                ADD id=28 left=[503,T] right=[358,Z]
                ADD id=29 left=[231,X] right=[329,G]
                ADD id=30 left=[220,P] right=[158,L]
                SWAP 12
                SWAP 15
                SWAP 14
                ADD id=31 left=[357,V] right=[202,H]
                ADD id=32 left=[353,G] right=[239,Y]
                ADD id=33 left=[340,X] right=[122,R]
                ADD id=34 left=[441,F] right=[266,R]
                ADD id=35 left=[219,T] right=[417,H]
                SWAP 29
                SWAP 30
                SWAP 4
                ADD id=36 left=[230,G] right=[373,B]
                ADD id=37 left=[388,W] right=[351,S]
                ADD id=38 left=[250,Z] right=[426,X]
                ADD id=39 left=[328,B] right=[428,V]
                ADD id=40 left=[392,Z] right=[345,S]
                SWAP 10
                SWAP 14
                SWAP 9
                SWAP 33
                ADD id=41 left=[387,Z] right=[374,T]
                ADD id=42 left=[386,P] right=[167,R]
                ADD id=43 left=[182,R] right=[397,H]
                ADD id=44 left=[254,W] right=[382,F]
                ADD id=45 left=[384,G] right=[378,Y]
                SWAP 7
                SWAP 27
                SWAP 9
                SWAP 3
                ADD id=46 left=[265,V] right=[247,S]
                ADD id=47 left=[197,L] right=[341,S]
                ADD id=48 left=[415,N] right=[128,S]
                ADD id=49 left=[366,J] right=[108,X]
                ADD id=50 left=[355,M] right=[420,Y]
                SWAP 3
                SWAP 36
                SWAP 32
                SWAP 40
                SWAP 39
                ADD id=51 left=[261,X] right=[352,N]
                ADD id=52 left=[115,T] right=[134,H]
                ADD id=53 left=[257,F] right=[440,Z]
                ADD id=54 left=[126,B] right=[253,M]
                ADD id=55 left=[258,Z] right=[437,N]
                SWAP 18
                SWAP 3
                SWAP 29
                SWAP 1
                SWAP 47
                ADD id=56 left=[432,Y] right=[436,F]
                ADD id=57 left=[396,J] right=[434,Z]
                ADD id=58 left=[339,B] right=[178,R]
                ADD id=59 left=[363,S] right=[248,X]
                ADD id=60 left=[416,F] right=[237,X]
                SWAP 35
                SWAP 40
                SWAP 58
                SWAP 36
                SWAP 48
                SWAP 15
                ADD id=61 left=[406,X] right=[390,H]
                ADD id=62 left=[139,Z] right=[431,B]
                ADD id=63 left=[422,B] right=[196,Z]
                ADD id=64 left=[371,Z] right=[269,Y]
                ADD id=65 left=[179,H] right=[215,Y]
                SWAP 12
                SWAP 54
                SWAP 44
                SWAP 60
                SWAP 47
                SWAP 36
                ADD id=66 left=[137,H] right=[346,P]
                ADD id=67 left=[224,H] right=[255,P]
                ADD id=68 left=[229,F] right=[107,V]
                ADD id=69 left=[259,N] right=[421,J]
                ADD id=70 left=[225,G] right=[162,B]
                SWAP 3
                SWAP 62
                SWAP 45
                SWAP 36
                SWAP 34
                SWAP 19
                SWAP 27
                ADD id=71 left=[527,B] right=[502,S]
                ADD id=72 left=[361,R] right=[267,J]
                ADD id=73 left=[347,Y] right=[111,L]
                ADD id=74 left=[156,X] right=[349,S]
                ADD id=75 left=[342,B] right=[180,G]
                SWAP 40
                SWAP 41
                SWAP 58
                SWAP 51
                SWAP 55
                SWAP 27
                SWAP 17
                ADD id=76 left=[547,H] right=[335,P]
                ADD id=77 left=[260,Y] right=[414,B]
                ADD id=78 left=[240,P] right=[187,X]
                ADD id=79 left=[245,H] right=[232,F]
                ADD id=80 left=[251,H] right=[234,F]
                SWAP 51
                SWAP 15
                SWAP 22
                SWAP 65
                SWAP 39
                SWAP 8
                SWAP 35
                SWAP 67
                ADD id=81 left=[159,R] right=[241,G]
                ADD id=82 left=[320,J] right=[411,T]
                ADD id=83 left=[479,H] right=[337,J]
                ADD id=84 left=[262,B] right=[402,J]
                ADD id=85 left=[492,W] right=[451,F]
                SWAP 23
                SWAP 15
                SWAP 37
                SWAP 11
                SWAP 16
                SWAP 29
                SWAP 17
                SWAP 79
                ADD id=86 left=[534,M] right=[222,S]
                ADD id=87 left=[391,T] right=[485,C]
                ADD id=88 left=[379,S] right=[135,W]
                ADD id=89 left=[176,S] right=[173,K]
                ADD id=90 left=[166,J] right=[102,W]
                SWAP 14
                SWAP 39
                SWAP 8
                SWAP 56
                SWAP 11
                SWAP 42
                SWAP 21
                SWAP 28
                SWAP 52
                ADD id=91 left=[360,L] right=[543,S]
                ADD id=92 left=[385,T] right=[303,Z]
                ADD id=93 left=[273,T] right=[283,J]
                ADD id=94 left=[235,V] right=[549,S]
                ADD id=95 left=[321,W] right=[307,J]
                SWAP 12
                SWAP 42
                SWAP 48
                SWAP 40
                SWAP 61
                SWAP 84
                SWAP 53
                SWAP 57
                SWAP 6
                ADD id=96 left=[465,W] right=[427,F]
                ADD id=97 left=[524,S] right=[449,Z]
                ADD id=98 left=[203,Y] right=[368,M]
                ADD id=99 left=[310,M] right=[448,P]
                ADD id=100 left=[101,B] right=[359,H]
                SWAP 64
                SWAP 73
                SWAP 15
                SWAP 31
                SWAP 36
                SWAP 4
                SWAP 40
                SWAP 94
                SWAP 20
                SWAP 81
                ADD id=101 left=[338,X] right=[430,Z]
                ADD id=102 left=[499,S] right=[506,Y]
                ADD id=103 left=[435,S] right=[398,L]
                ADD id=104 left=[480,T] right=[144,G]
                ADD id=105 left=[292,X] right=[495,V]
                SWAP 53
                SWAP 45
                SWAP 21
                SWAP 43
                SWAP 95
                SWAP 83
                SWAP 54
                SWAP 103
                SWAP 50
                SWAP 10
                ADD id=106 left=[413,M] right=[198,X]
                ADD id=107 left=[412,F] right=[243,R]
                ADD id=108 left=[433,V] right=[362,X]
                ADD id=109 left=[423,G] right=[324,N]
                ADD id=110 left=[150,Z] right=[227,M]
                SWAP 107
                SWAP 97
                SWAP 106
                SWAP 31
                SWAP 59
                SWAP 57
                SWAP 44
                SWAP 65
                SWAP 15
                SWAP 104
                SWAP 101
                ADD id=111 left=[409,P] right=[487,R]
                ADD id=112 left=[131,W] right=[551,J]
                ADD id=113 left=[193,N] right=[403,M]
                ADD id=114 left=[395,R] right=[372,M]
                ADD id=115 left=[216,M] right=[217,V]
                SWAP 63
                SWAP 15
                SWAP 91
                SWAP 114
                SWAP 51
                SWAP 55
                SWAP 38
                SWAP 98
                SWAP 107
                SWAP 17
                SWAP 44
                ADD id=116 left=[455,H] right=[481,V]
                ADD id=117 left=[188,B] right=[533,T]
                ADD id=118 left=[281,G] right=[380,P]
                ADD id=119 left=[525,L] right=[272,V]
                ADD id=120 left=[256,B] right=[174,V]
                SWAP 107
                SWAP 32
                SWAP 23
                SWAP 83
                SWAP 81
                SWAP 53
                SWAP 66
                SWAP 43
                SWAP 38
                SWAP 92
                SWAP 88
                SWAP 35
                ADD id=121 left=[512,P] right=[364,M]
                ADD id=122 left=[383,L] right=[138,B]
                ADD id=123 left=[356,V] right=[171,L]
                ADD id=124 left=[498,M] right=[125,T]
                ADD id=125 left=[160,W] right=[268,V]
                SWAP 20
                SWAP 25
                SWAP 40
                SWAP 52
                SWAP 73
                SWAP 18
                SWAP 58
                SWAP 27
                SWAP 39
                SWAP 57
                SWAP 55
                SWAP 97
                ADD id=126 left=[282,P] right=[334,B]
                ADD id=127 left=[148,F] right=[472,H]
                ADD id=128 left=[190,R] right=[114,V]
                ADD id=129 left=[535,T] right=[473,N]
                ADD id=130 left=[319,W] right=[513,M]
                SWAP 32
                SWAP 3
                SWAP 118
                SWAP 84
                SWAP 85
                SWAP 23
                SWAP 113
                SWAP 53
                SWAP 103
                SWAP 25
                SWAP 107
                SWAP 26
                SWAP 78
                ADD id=131 left=[296,N] right=[445,F]
                ADD id=132 left=[333,Z] right=[484,T]
                ADD id=133 left=[539,J] right=[189,B]
                ADD id=134 left=[312,X] right=[218,L]
                ADD id=135 left=[460,T] right=[354,F]
                SWAP 20
                SWAP 101
                SWAP 71
                SWAP 115
                SWAP 128
                SWAP 92
                SWAP 88
                SWAP 12
                SWAP 117
                SWAP 42
                SWAP 124
                SWAP 40
                SWAP 27
                ADD id=136 left=[314,X] right=[104,W]
                ADD id=137 left=[438,T] right=[200,H]
                ADD id=138 left=[471,A] right=[429,R]
                ADD id=139 left=[309,V] right=[418,B]
                ADD id=140 left=[401,R] right=[228,X]
                SWAP 71
                SWAP 99
                SWAP 12
                SWAP 97
                SWAP 76
                SWAP 74
                SWAP 45
                SWAP 35
                SWAP 2
                SWAP 55
                SWAP 9
                SWAP 27
                SWAP 138
                SWAP 88
                ADD id=141 left=[419,T] right=[164,P]
                ADD id=142 left=[410,Y] right=[507,H]
                ADD id=143 left=[263,S] right=[500,M]
                ADD id=144 left=[530,W] right=[400,H]
                ADD id=145 left=[493,W] right=[488,M]
                SWAP 67
                SWAP 95
                SWAP 124
                SWAP 18
                SWAP 92
                SWAP 43
                SWAP 135
                SWAP 24
                SWAP 128
                SWAP 102
                SWAP 129
                SWAP 47
                SWAP 133
                SWAP 138
                ADD id=146 left=[308,B] right=[186,M]
                ADD id=147 left=[152,P] right=[119,W]
                ADD id=148 left=[425,V] right=[546,W]
                ADD id=149 left=[301,V] right=[375,G]
                ADD id=150 left=[184,W] right=[478,P]
                SWAP 10
                SWAP 68
                SWAP 82
                SWAP 35
                SWAP 79
                SWAP 33
                SWAP 96
                SWAP 36
                SWAP 52
                SWAP 109
                SWAP 64
                SWAP 72
                SWAP 40
                SWAP 23
                SWAP 50
                ADD id=151 left=[293,J] right=[136,Z]
                ADD id=152 left=[133,T] right=[109,Y]
                ADD id=153 left=[517,Z] right=[100,T]
                ADD id=154 left=[365,Z] right=[276,N]
                ADD id=155 left=[529,T] right=[181,S]
                SWAP 29
                SWAP 26
                SWAP 98
                SWAP 128
                SWAP 31
                SWAP 109
                SWAP 141
                SWAP 92
                SWAP 27
                SWAP 36
                SWAP 23
                SWAP 145
                SWAP 127
                SWAP 40
                SWAP 50
                ADD id=156 left=[370,Y] right=[161,F]
                ADD id=157 left=[381,N] right=[142,X]
                ADD id=158 left=[211,M] right=[275,R]
                ADD id=159 left=[477,J] right=[376,B]
                ADD id=160 left=[332,M] right=[331,P]
                SWAP 112
                SWAP 62
                SWAP 47
                SWAP 130
                SWAP 79
                SWAP 56
                SWAP 33
                SWAP 147
                SWAP 131
                SWAP 2
                SWAP 73
                SWAP 60
                SWAP 111
                SWAP 141
                SWAP 42
                SWAP 46
                ADD id=161 left=[330,F] right=[350,B]
                ADD id=162 left=[537,N] right=[305,V]
                ADD id=163 left=[336,W] right=[155,R]
                ADD id=164 left=[290,L] right=[489,P]
                ADD id=165 left=[553,G] right=[315,V]
                SWAP 75
                SWAP 139
                SWAP 41
                SWAP 15
                SWAP 124
                SWAP 147
                SWAP 60
                SWAP 122
                SWAP 90
                SWAP 157
                SWAP 55
                SWAP 38
                SWAP 83
                SWAP 36
                SWAP 164
                SWAP 82
                ADD id=166 left=[280,B] right=[295,M]
                ADD id=167 left=[348,Y] right=[212,M]
                ADD id=168 left=[424,F] right=[439,B]
                ADD id=169 left=[393,M] right=[466,Z]
                ADD id=170 left=[367,N] right=[555,Y]
                SWAP 98
                SWAP 138
                SWAP 35
                SWAP 80
                SWAP 84
                SWAP 116
                SWAP 18
                SWAP 37
                SWAP 59
                SWAP 70
                SWAP 82
                SWAP 52
                SWAP 49
                SWAP 38
                SWAP 21
                SWAP 4
                SWAP 164
                ADD id=171 left=[369,H] right=[299,Y]
                ADD id=172 left=[298,G] right=[545,H]
                ADD id=173 left=[457,H] right=[528,J]
                ADD id=174 left=[117,W] right=[510,X]
                ADD id=175 left=[177,L] right=[454,V]
                SWAP 101
                SWAP 85
                SWAP 36
                SWAP 32
                SWAP 169
                SWAP 92
                SWAP 15
                SWAP 163
                SWAP 40
                SWAP 167
                SWAP 110
                SWAP 162
                SWAP 135
                SWAP 155
                SWAP 83
                SWAP 120
                SWAP 5
                ADD id=176 left=[446,R] right=[521,L]
                ADD id=177 left=[313,W] right=[515,J]
                ADD id=178 left=[536,X] right=[192,J]
                ADD id=179 left=[325,X] right=[542,L]
                ADD id=180 left=[552,X] right=[447,L]
                SWAP 118
                SWAP 21
                SWAP 152
                SWAP 107
                SWAP 31
                SWAP 56
                SWAP 38
                SWAP 87
                SWAP 145
                SWAP 116
                SWAP 168
                SWAP 111
                SWAP 171
                SWAP 133
                SWAP 155
                SWAP 53
                SWAP 149
                SWAP 164
                ADD id=181 left=[175,F] right=[311,R]
                ADD id=182 left=[288,R] right=[464,B]
                ADD id=183 left=[516,Y] right=[316,G]
                ADD id=184 left=[520,P] right=[286,J]
                ADD id=185 left=[317,B] right=[468,N]
                SWAP 167
                SWAP 40
                SWAP 35
                SWAP 137
                SWAP 33
                SWAP 124
                SWAP 13
                SWAP 23
                SWAP 125
                SWAP 29
                SWAP 152
                SWAP 60
                SWAP 37
                SWAP 184
                SWAP 96
                SWAP 102
                SWAP 79
                SWAP 169
                ADD id=186 left=[284,H] right=[285,X]
                ADD id=187 left=[554,W] right=[183,P]
                ADD id=188 left=[508,F] right=[129,L]
                ADD id=189 left=[165,P] right=[302,R]
                ADD id=190 left=[540,H] right=[526,W]
                SWAP 130
                SWAP 39
                SWAP 143
                SWAP 34
                SWAP 104
                SWAP 15
                SWAP 2
                SWAP 63
                SWAP 115
                SWAP 91
                SWAP 49
                SWAP 13
                SWAP 66
                SWAP 1
                SWAP 114
                SWAP 183
                SWAP 32
                SWAP 76
                SWAP 158
                ADD id=191 left=[482,H] right=[201,L]
                ADD id=192 left=[459,P] right=[120,F]
                ADD id=193 left=[531,M] right=[476,W]
                ADD id=194 left=[463,X] right=[519,H]
                ADD id=195 left=[194,F] right=[522,W]
                SWAP 172
                SWAP 179
                SWAP 146
                SWAP 118
                SWAP 28
                SWAP 21
                SWAP 90
                SWAP 155
                SWAP 24
                SWAP 183
                SWAP 139
                SWAP 143
                SWAP 77
                SWAP 154
                SWAP 51
                SWAP 174
                SWAP 124
                SWAP 66
                SWAP 27
                ADD id=196 left=[470,F] right=[304,B]
                ADD id=197 left=[501,Z] right=[151,T]
                ADD id=198 left=[318,M] right=[518,G]
                ADD id=199 left=[491,N] right=[862,T]
                ADD id=200 left=[609,X] right=[777,P]
                SWAP 89
                SWAP 191
                SWAP 29
                SWAP 194
                SWAP 172
                SWAP 181
                SWAP 162
                SWAP 101
                SWAP 11
                SWAP 54
                SWAP 55
                SWAP 124
                SWAP 68
                SWAP 106
                SWAP 47
                SWAP 150
                SWAP 57
                SWAP 64
                SWAP 71
                SWAP 198
            """.trimIndent()
            data class Node(var rank: Int, var symbol: Char) : Comparable<Node> {
                override fun compareTo(other: Node): Int {
                    return this.rank - other.rank
                }
            }

            val leftTree: BST<Node> = BST()
            val rightTree: BST<Node> = BST()

            input.lines().forEach { line ->
                val regex = Regex("""ADD id=(\d+) left=\[(\d+),(.)\] right=\[(\d+),(.)\]""")
                val matchResult = regex.matchEntire(line)
                if (matchResult != null) {
                    val (id, leftRank, leftSymbol, rightRank, rightSymbol) = matchResult.destructured
                    leftTree.insert(id.toInt(), Node(leftRank.toInt(), leftSymbol.single()))
                    rightTree.insert(id.toInt(), Node(rightRank.toInt(), rightSymbol.single()))
                } else {
                    val swapRegex = Regex("""SWAP (\d+)""")
                    val swapMatch = swapRegex.matchEntire(line)
                    if (swapMatch != null) {
                        val (id) = swapMatch.destructured
                        val leftNode = leftTree.nodesById[id.toInt()]
                        val rightNode = rightTree.nodesById[id.toInt()]
                        if (leftNode == leftTree.root) {
                            leftTree.root = rightNode
                            leftTree.nodesById[id.toInt()] = rightNode!!
                            rightTree.root = leftNode
                            rightTree.nodesById[id.toInt()] = leftNode!!
                        } else {
                            if (leftNode?.parent?.left == leftNode) {
                                leftNode?.parent?.left = rightNode
                            } else {
                                leftNode?.parent?.right = rightNode
                            }
                            if (rightNode?.parent?.left == rightNode) {
                                rightNode?.parent?.left = leftNode
                            } else {
                                rightNode?.parent?.right = leftNode
                            }

                            val tempParent = leftNode?.parent
                            leftNode?.parent = rightNode?.parent
                            rightNode?.parent = tempParent
                            leftTree.nodesById[id.toInt()] = rightNode!!
                            rightTree.nodesById[id.toInt()] = leftNode!!
                        }
                    }
                }
            }

            val leftMessages: MutableList<MutableList<Char>> = mutableListOf()
            val rightMessages: MutableList<MutableList<Char>> = mutableListOf()
            val leftTreeAction: (Node, Int) -> Unit = { symbol: Node, depth: Int ->
                if (depth >= leftMessages.size) {
                    leftMessages.add(mutableListOf())
                }
                leftMessages[depth].add(symbol.symbol)
            }
            val rightTreeAction: (Node, Int) -> Unit = { symbol: Node, depth: Int ->
                if (depth >= rightMessages.size) {
                    rightMessages.add(mutableListOf())
                }
                rightMessages[depth].add(symbol.symbol)
            }
            leftTree.traverse(leftTreeAction)
            rightTree.traverse(rightTreeAction)
            val leftSize = leftMessages.maxOf { it.size }
            val rightSize = rightMessages.maxOf { it.size }
            println(leftMessages.first { it.size == leftSize }.joinToString(separator = "") + rightMessages.first { it.size == rightSize }
                .joinToString(separator = ""))
        }
    }
}
