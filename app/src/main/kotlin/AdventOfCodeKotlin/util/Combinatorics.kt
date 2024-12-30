package AdventOfCodeKotlin.util

class Combinatorics {
    companion object {
        fun <T> combinations(list: List<T>, n: Int): List<List<T>> {
            if (n == 0) return listOf(emptyList())
            if (n == list.size) return listOf(list)
            if (n > list.size) return emptyList()
            if (n == 1) return list.map { listOf(it) }
            val head = list.first()
            val tail = list.drop(1)
            return combinations(tail, n - 1).map { it + head } + combinations(tail, n)
        }
    }
}