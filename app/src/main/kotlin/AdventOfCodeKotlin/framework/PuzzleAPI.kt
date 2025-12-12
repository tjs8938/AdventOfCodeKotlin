package AdventOfCodeKotlin.framework

interface PuzzleAPI {
    fun get(params: ParamProvider, user: User): String

    fun post(answer: String, part: Int, params: ParamProvider, user: User)
}