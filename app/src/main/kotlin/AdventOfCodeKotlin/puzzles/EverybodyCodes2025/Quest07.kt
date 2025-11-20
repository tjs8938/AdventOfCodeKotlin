package AdventOfCodeKotlin.puzzles.EverybodyCodes2025

class Quest07 {
    companion object {
        fun part1() {
            val (nameStr, ruleStr) = """
                Nyoris,Lazoris,Nycalyx,Lazcalyx,Beloris,Belcalyx,Nythyris,Lazthyris,Belthyris

                y > t,r,x,b
                h > y
                L > a
                e > b
                o > r
                i > s
                z > c,t,o
                B > e
                a > b,l
                N > y
                c > a
                l > y,c,t,o
                t > h
                r > i
            """.trimIndent().split("\n\n")
            val names = nameStr.split(",")
            val rules = ruleStr.lines().associate { line ->
                val parts = line.split(">")
                val from = parts[0].trim()[0]
                val to = parts[1].trim().split(",").map { it.trim()[0] }
                from to to
            }
            println(names.filter { name ->
                for (i in 1 until name.length) {
                    if (!rules[name[i - 1]]!!.contains(name[i])) {
                        return@filter false
                    }
                }
                return@filter true
            }.first())
        }

        fun part2() {
            val (nameStr, ruleStr) = """
                Rythagrath,Rythluth,Rythgyth,Tyrmyr,Nylagrath,Rahagrath,Kyndrith,Tyrmirix,Rahluth,Rahdrith,Rahgyth,Nylmyr,Arkdrith,Tyrmyr,Arkaes,Tyrgyth,Rahaes,Rahmyr,Arkmyr,Arkluth,Rahmyr,Arkagrath,Rahzyth,Tyraes,Rahmirix,Arkzyth,Arkmirix,Nylmyr,Tyrzyth,Jorathzyth,Jorathmyr,Tyrluth,Kyngyth,Nyldrith,Kalaes,Tyrdrith,Zyrluth,Nylaes,Nylluth,Rythmirix,Zyrmirix,Kynmyr,Rythdrith,Kalluth,Rythzyth,Rythmyr,Zyragrath,Arkmyr,Tyrdrith,Nylzyth,Tyragrath,Kalmirix,Dalzyth,Jorathdrith,Nylgyth,Nyldrith,Nylmirix,Rythaes,Kalmyr,Zyrmyr,Daldrith,Rahdrith,Jorathagrath,Rythmyr,Arkgyth,Rythdrith,Jorathmirix,Zyrzyth,Kaldrith,Zyrgyth,Kaldrith,Kynluth,Zyrdrith,Kynaes,Zyraes,Kalmyr,Zyrmyr,Jorathaes,Zyrdrith,Kyndrith,Kalzyth,Dalmirix,Jorathgyth,Jorathluth,Kynmyr,Dalaes,Daldrith,Kalagrath,Dalmyr,Dalluth,Dalagrath,Dalmyr,Kynzyth,Jorathmyr,Jorathdrith,Kynmirix,Kalgyth,Dalgyth,Kynagrath,Arkdrith

                D > a
                g > r,y
                d > r
                y > n,v,r,t
                K > y,a
                e > s
                J > o
                z > y
                A > r
                R > a,y
                T > y
                m > y,i
                u > t
                N > y
                k > a,l,m,g,z,d
                Z > y
                h > a,l,m,g,z,d
                n > d,a,l,m,g,z
                t > h
                i > t,r,x
                l > a,l,u,m,g,z,d
                r > i,a,l,m,g,z,d,v
                a > g,t,e,v
                o > v
            """.trimIndent().split("\n\n")
            val names = nameStr.split(",")
            val rules = ruleStr.lines().associate { line ->
                val parts = line.split(">")
                val from = parts[0].trim()[0]
                val to = parts[1].trim().split(",").map { it.trim()[0] }
                from to to
            }
            println(
                names
                    .mapIndexed { index, name ->
                        for (i in 1 until name.length) {
                            if (!rules[name[i - 1]]!!.contains(name[i])) {
                                return@mapIndexed index to false
                            }
                        }
                        return@mapIndexed index to true
                    }
                    .filter { it.second }
                    .sumOf { it.first + 1 })
        }

        fun part3() {
            val (nameStr, ruleStr) = """
                Ny,Nyl,Nyth,Nyss,Nyrix,Pyr,Ild,Tal,Durn,Fen,Norak,Drel,Val,Nald,Thyros,Adal,Felmar,Elt,Haz,Rythan

                H > a
                l > i,d,s,g,a,k,f,v,e,m
                s > i,s,g,a,k,f,v,e
                v > e
                I > l
                f > e
                z > v,s,g,a,k,f,e
                a > z,x,d,r,e,v,k,l,n
                x > s,g,a,k,f,v,e
                T > a,h
                h > s,g,a,k,f,v,e
                d > a,r,s,g,k,f,v,e
                t > h,s,g,a,k,f,v,e
                y > v,r
                D > u,r
                F > e
                i > n,s,x
                k > r,a,e,s,g,k,f,v
                n > n,a,s,g,k,f,v,e
                e > r,l,t,v
                E > l
                m > a
                P > y
                r > a,i,s,g,k,f,v,e,n,o
                V > a
                u > v
                R > y
                o > n,v,s
                g > o,n
                A > d
                N > y,o,a
            """.trimIndent().split("\n\n")
            val rules = ruleStr.lines().associate { line ->
                val parts = line.split(">")
                val from = parts[0].trim()[0]
                val to = parts[1].trim().split(",").map { it.trim()[0] }
                from to to
            }
            val prefixes = nameStr.split(",")
                .filter { name ->
                for (i in 1 until name.length) {
                    if (!rules[name[i - 1]]!!.contains(name[i])) {
                        return@filter false
                    }
                }
                return@filter true
            }

            val goodNames = mutableSetOf<String>()

            fun goodNames(name: String) {
                if (name.length > 11) {
                    return
                }
                if (name.length >= 7) {
                    goodNames.add(name)
                }
                rules[name.last()]?.forEach { nextChar ->
                    goodNames(name + nextChar)
                }
            }

            prefixes.forEach { goodNames(it) }

            println(goodNames.size)
        }
    }
}

fun main() {
    Quest07.part1()
    Quest07.part2()
    Quest07.part3()
}
