package AdventOfCodeKotlin.util

class MemoizedFunction<P, R>(val f: MemoizedFunction<P, R>.(P) -> R) : (P) -> R {
    private val cache = mutableMapOf<P, R>()

    override fun invoke(p: P): R {
        return cache.getOrPut(p) { f(p) }
    }

    fun clear() = cache.clear()

    companion object {
        fun <P, R> memoize(f: MemoizedFunction<P, R>.(P) -> R): MemoizedFunction<P, R> {
            return MemoizedFunction(f)
        }
    }
}