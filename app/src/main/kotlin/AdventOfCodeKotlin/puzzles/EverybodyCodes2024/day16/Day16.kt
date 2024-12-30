package AdventOfCodeKotlin.puzzles.EverybodyCodes2024.day16

import AdventOfCodeKotlin.util.MemoizedFunction
import AdventOfCodeKotlin.util.MemoizedFunction.Companion.memoize
import AdventOfCodeKotlin.util.lcm
import kotlin.math.max
import kotlin.math.min

fun main() {
//    Day16.part1()
//    Day16.part2()
    Day16.part3()
}

class Day16 {
    companion object {
        fun part1() {
            val input = """
10,3,11,20

<.* *,< >_< >:*
>:* *:* *:* <:>
^,* ^_< >_< ^_<
*,< -_- -:> ^,*
^_< ^,* >:* <.*
>:* <.* <:> -_-
*:* -_- *:* ^,*
<:> ^,* -_- *:*
-:> <:> >_< -:>
>_< ^_< -_- ^,*
    -:> >:*    
    -:> -_-    
    ^_< ^,*    
        -:>    
        ^,*    
        ^,*    
        -:>    
        -:>    
        *,<    
        <:>    
        ^_<    
        *,<    
        ^_<    
        <:>    
            """.trimIndent().split("\n")

            val spins = input[0].split(",").map { it.toInt() }
            val initRows = input.drop(2).map { it.windowed(3, 4) }
            val catFaces = spins.indices.map { i -> initRows.mapNotNull { it[i].takeIf { it.isNotBlank() } } }

            val result =
                spins.indices.associateWith { (spins[it] * 100) % catFaces[it].size }.map { (i, j) -> catFaces[i][j] }
                    .joinToString(" ")
            println(result)


        }

        fun part2() {
            val input = """
43,67,53,83,59,71,73,79,61,47

0.* @:T Y:] x,& (:\ ^,& S.0 +;] *;~ O:/ 
<,( +.# ^.] X_> ${'$'}:X ].${'$'} (;( }_/ <_X Q.0 
":T G:T G;\ P_Q ]:Q '_x 0.[ |:[ @;P =.[ 
{;} X,> S.< >,x O,{ ${'$'}.] =.< S.S ]:o [,& 
':0 /,+ #.${'$'} Y:[ '.' +.P (;x O_' (.+ \,~ 
${'$'},I U_- ~_] 0,/ &_O %,] +:Q +;G *,0 Y;} 
X.{ 0;^ &:% ",I }.] -," Y:" -:T {;O /:% 
P;& ^_( G;U Y;} Y,X Y:' @_' ^_[ G.} +,+ 
\,O @,{ P;- ];~ X.^ T:~ {;o o_O G.^ >:" 
[,* ^_" ]_% Y.G '_] \.- *.S =,+ (_${'$'} -:[ 
\;X *_^ ~,@ T;o +.> Q:+ ^_| x,G I_| 0:T 
Q." },% Q;[ =;${'$'} \,${'$'} /;@ @;] };% X.= ).% 
${'$'}_Q o,- +,% S.0 +;G [,/ 0_S <,` \_@ +:# 
~_I @.' \_` {;- ],' %_% I_) ).G ${'$'};x Y,S 
&,& "_# %:S G_${'$'} -_& *,& >_O {,U ];x I." 
0.${'$'} >:Q P:/ o,( (," ".# >;% }:+ &,' X_o 
{:0 I;( \;T [:` O;" X;| O,( -;~ U:> /,o 
<;I x;& /.& U;o G;I |_Q I;@ X_S |,] |_" 
#:% >,/ %_& X:S }:* X;& U:@ 0_* T_o (.~ 
P;~ ':o @;P %_X @;T U_% #:{ U;S Q_^ G.U 
    Q,T (,( )_\ @;X     I:% I." (;* @,X 
    Y,G =:< ":% S:Y     [:I S,Q U:" x_O 
    *,= =,* >;- '_&     \_U %;" ~:< [;Q 
    T_^ {.> Y_G <_&     Q.P P,T ~.% @,| 
    O.+ P.> -.{ X_=     0.@ @;{ @;@ `,O 
    [,^ G_>     [.>         I_* T;/ U,# 
    I,> Y_]     *;+         T.{ \:+ T;# 
    I,' %;-     `_${'$'}         ',U ${'$'};[ S_( 
    ";x =_/     ),\         [:~ {,{ %:X 
    <;I G:'     P_(         };] T_I `,{ 
                "_|         I_" /_^ (.X 
                X_I         {.Q >,Q ~,` 
                <.@         ].^ },| >;G 
                Y_O         o;{ o:| =_T 
                ]:Y         Y:@ U;' (.S 
                Q_[                 I;* 
                {;~                 (_} 
                T.Y                 o:x 
                {:'                 O;- 
                                    U;#
            """.trimIndent().split("\n")

            val spins = input[0].split(",").map { it.toInt() }
            val initRows = input.drop(2).map { it.windowed(3, 4) }
            val catFaces = spins.indices.map { i -> initRows.mapNotNull { it[i].takeIf { it.isNotBlank() } } }

            val wheelResetCounts = spins.mapIndexed { index, spinCount ->
                lcm(
                    (spinCount % catFaces[index].size).toLong(),
                    catFaces[index].size.toLong()
                )
            }
            val totalResetCount = wheelResetCounts.reduce { acc, i -> lcm(acc, i) }

            var spinCount = 0L
            var wheelPositions = spins.map { 0 }.toMutableList()
            var totalByteCoins = 0L
            var remainderCoins = 0L
            while (spinCount < totalResetCount) {

                wheelPositions = wheelPositions
                    .mapIndexed { index, wheelPosition -> (wheelPosition + spins[index]) % catFaces[index].size }
                    .toMutableList()
                spinCount++

                val spinCoins = calcCoins(catFaces, wheelPositions)


                totalByteCoins += spinCoins
                if (spinCount < (202420242024L % totalResetCount)) {
                    remainderCoins += spinCoins
                }
            }

            assert(wheelPositions.all { it == 0 })
            totalByteCoins *= 202420242024L / spinCount
            totalByteCoins += remainderCoins

            println(totalByteCoins)
        }

        private fun calcCoins(
            catFaces: List<List<String>>,
            wheelPositions: List<Int>
        ): Int {
            val spinCoins = catFaces.mapIndexed { index, cats -> cats[wheelPositions[index]] }.flatMap {
                val eyes = it.toMutableList()
                eyes.removeAt(1)
                eyes
            }.groupingBy { it }.eachCount()
                .filterValues { it >= 3 }
                .map { it.value - 2 }.sum()
            return spinCoins
        }

        fun part3() {
            val input = """
61,53,59,43,47

^:${'$'} >.< <.- o.o ${'$'}:< 
*:^ <:^ =.= ${'$'}.o >.* 
o.= ${'$'}.> ^.> >.o ^.${'$'} 
=.> >:${'$'} =.* -.o >.< 
*.= <:> -:- -.< <.* 
${'$'}.o ^:${'$'} -:* -.- <.< 
*.< >:^ ${'$'}:< o:= ${'$'}:^ 
o:= ${'$'}.${'$'} <:o ${'$'}:^ o:- 
*:= ^:> ^:> o.^ ^:* 
^:o ^:${'$'} >:> ${'$'}.> ^:> 
-.^ ^:< ${'$'}:= ${'$'}:^ -.${'$'} 
*.${'$'} <.* >:^ ^.< ^.^ 
^:> ${'$'}:= *.o o.- <.- 
*.o =:< o.= >:o >:= 
o.> >:${'$'} =.o <:= ${'$'}:> 
<:< <:o o.- <.o >.* 
>:* ^.${'$'} >:= >:^ <.o 
-.- o:o -.o ${'$'}.o -.> 
<:o -:${'$'} *.^ -:> *:^ 
>.* =:> *:* ^:- ${'$'}:^ 
>.o -:> ^:^ o.^ -.= 
o:o ${'$'}:= ${'$'}:= >.^ *.o 
>.= <:o ^:^ <:${'$'} >.o 
-:^ ^.> >.${'$'} >:- ^.${'$'} 
^:* ^.^ <.> -:* >:^ 
=.= *:> o:* -.- ^:- 
^.* >:${'$'} >.o *.< <.${'$'} 
<:= o.^ ${'$'}.${'$'} ${'$'}:^ ${'$'}:${'$'} 
^:* =:* *:${'$'} ${'$'}.> -.* 
*:> <:^ ${'$'}.o ^.- *.< 
    -.> =.o *.= >:o 
    -:> -.* <:= ^.o 
    <:= =.^ >:> o:${'$'} 
    *:^ =:- ${'$'}:^ ^.< 
    =.< =:< *:o =.* 
    o.- o.^     =.= 
    =:> ^:o     <:${'$'} 
    *.* >:=     *.* 
    *.^ *:-     *.- 
    ${'$'}.< ^.=     -.${'$'} 
        =.=     ^.* 
        >.${'$'}     >:< 
        ${'$'}:*     ^:- 
        <.${'$'}     >.^ 
        ${'$'}:=     -:o 
                -:< 
                ${'$'}.- 
                *:* 
                ${'$'}.= 
                =:${'$'}
""".trimIndent().split("\n")

            val spins = input[0].split(",").map { it.toInt() }
            val initRows = input.drop(2).map { it.windowed(3, 4) }
            val catFaces = spins.indices.map { i -> initRows.mapNotNull { it[i].takeIf { it.isNotBlank() } } }

            val leftLeverActions = listOf(-1, 0, 1)
            var calculatedCoins = memoize { wheelPositions: List<Int> -> calcCoins(catFaces, wheelPositions) }

            var pullLeverMemoized: MemoizedFunction<Pair<Int, List<Int>>, Pair<Long, Long>>? = null

            fun pullLever(
                depth: Int,
                wheelPositions: List<Int>
            ): Pair<Long, Long> {
                if (depth == 256) {
                    return 0L to 0L
                }

                return leftLeverActions.map { leftLever ->
                    val newWheelPositions = wheelPositions.mapIndexed { index, wheelPosition ->
                        (wheelPosition + spins[index] + leftLever) % catFaces[index].size
                    }
                    val spinCoins = calculatedCoins(newWheelPositions)
                    val nextSpins = pullLeverMemoized?.let { it(depth + 1 to newWheelPositions) }!!
                    nextSpins.first + spinCoins to nextSpins.second + spinCoins
                }.reduce { acc, pair -> min(acc.first, pair.first) to max(acc.second, pair.second) }
            }

            pullLeverMemoized = memoize { (depth, wheel) -> pullLever(depth, wheel) }
            val result = pullLeverMemoized(0 to spins.map { 0 })
            println("${result.second} ${result.first}")
        }
    }
}