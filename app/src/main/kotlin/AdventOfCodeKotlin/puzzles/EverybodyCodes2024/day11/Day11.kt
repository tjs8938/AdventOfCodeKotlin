package year2024.day11

fun main() {
    Day11.part1()
    Day11.part2()
    Day11.part3()
}

class Day11 {
    companion object {
        fun part1() {
            val input = """
                A:O,O,E
                O:Y,E
                Y:U,I,E
                I:E,U
                E:U,A,I
                U:I,O
            """.trimIndent().split("\n").map {
                val (key, value) = it.split(":")
                key to value.split(",")
            }.toMap()

            var result = mutableMapOf("A" to 1)
            repeat(4) {
                result = result.flatMap { (key, value) ->
                    input[key]!!.map { it to value }
                }.groupingBy { it.first }.fold(0) { acc, (_, value) -> acc + value }.toMutableMap()
            }
            println(result.values.sum())
        }

        fun part2() {
            val input = """
                Z:G,N,J,C
                J:K,V,W
                N:Z,K,X,C
                K:V,F,B
                D:P,H,W
                X:K,W,B,P
                S:Q,G,F
                H:K,B,C
                M:L,P,G,L
                T:P,M,W,W
                W:S,D,K,F
                G:B,N,W
                V:G,X,Z
                C:R,F,L,F
                R:D,P,H,P
                L:H,S,B
                F:X,Q,R
                B:H,G,J
                P:D,H,R,G
                Q:V,G,T,R
            """.trimIndent().split("\n").map {
                val (key, value) = it.split(":")
                key to value.split(",")
            }.toMap()

            var result = mutableMapOf("Z" to 1)
            repeat(10) {
                result = result.flatMap { (key, value) ->
                    input[key]!!.map { it to value }
                }.groupingBy { it.first }.fold(0) { acc, (_, value) -> acc + value }.toMutableMap()
            }
            println(result.values.sum())
        }

        fun part3() {
            val input = """
                GVH:LXF,GWM,MPM,CWD,LRJ
                GVQ:HXJ,TNG,MCW
                JXF:NCV,TZN,TBJ,QCG,DXW
                KBC:JXF,FZJ,HWG
                CWD:DGH,BTG,DGH,PRW,LSG
                KKK:MZJ,LHK,LSG
                DML:NNJ,FXS,XKW,VQH,QCG
                WSB:QDL,BNZ,HXC
                GLL:RPJ,ZZJ,RPJ
                FBH:WSL,QDL,MCW
                BJS:PLG,XZX,JXF
                BTG:TZT,BVF,GDR
                PDB:RFJ,WPD,LXF
                NCV:XKW,NJR,JGZ
                LFZ:FBH,TSZ,QDL
                RFJ:DGH,BNZ,FVQ
                QDL:BNZ,FVQ,WSB
                FGH:LTW,GSV,BJS
                KGV:HPZ,LWB,TBJ,HJZ,GSV
                TSZ:LRJ,HJZ,XZX,BJS,GZB
                VQH:GWM,RWH,WSL,QDL,FZJ
                GWM:RFJ,XKW,HMN,PLR,RWH
                XZX:HXC,RFJ,TNG,GDR,SXC
                HXC:GVQ,KKK,VRV,TVZ,PRW
                KLV:TNG,FTV,MZJ
                DGH:NNJ,LFZ,TVB,QDL,QCG
                NNJ:DML,PVL,PLG
                LWB:QDL,RDJ,FWZ,LWB,XZX
                TZT:PLG,LHG,TZT,PDB,WSB
                BVF:MPM,KKK,LRJ,GWM,LFZ
                TSN:DML,TVB,FBH,BJS,TNG
                BJC:VXD,DGH,TBJ,RFJ,HPZ
                TNG:NNJ,TVZ,FWZ
                HJZ:FTH,VLK,TVB,QZL,TSZ
                BVS:FBH,TKM,DML
                GZB:HXC,LTW,HJW
                PVL:MZJ,ZRP,LHK
                LHG:BJC,TZT,CWD
                PLG:HDT,LHG,FTH,JGZ,TBJ
                RPJ:KKK,MCW,HJZ
                QCG:HJW,LFZ,PLR
                TVB:KDC,JGZ,NNJ,GZB,BCB
                LRJ:XZX,VQH,LHG
                MCW:HJW,FZJ,FBH
                TBJ:GVQ,HPZ,GLL
                PBQ:HXC,PDB,WPD,ZZJ,JGZ
                HXJ:MZJ,BJC,PDB,VLK,PLR
                ZGT:PBQ,MZJ,JCJ,TSZ,VRV
                BFQ:NNJ,PLR,BCB
                LTW:PLR,VWH,GZB
                SHT:TKM,NNJ,NNJ
                FZJ:VXD,JVS,FBH,JVS,MCW
                TVZ:FVQ,TBJ,VRV
                LSG:HPZ,FTH,NNJ
                FTH:TZT,RWH,LRJ,HJZ,HMN
                GSV:ZRP,FGH,TZT,QDL,PBQ
                HJW:LTW,NNJ,WSB,FQM,MPM
                BNZ:SXC,HMN,HPZ,LHK,WPD
                MPM:HXC,QZL,PML,BJC,LHG
                QZL:TZN,JKM,VQH,RFJ,FZJ
                LHK:LRJ,HXJ,ZZJ
                XKW:HWG,DXW,FZJ,TSZ,DXW
                VWH:VQH,PLR,GVH
                MZJ:TSN,LTW,FBH,GSV,FQM
                JKM:XKW,LHG,FVQ
                FQM:KLV,VQH,TBJ
                BCB:ZZJ,HXJ,RWH,FZJ,XKW
                FVQ:ZGT,PLR,JGZ,FGH,LFZ
                TKM:KDC,HDT,MCW
                CTH:SXC,FWZ,KKK
                LXF:ZZJ,VLK,FXS,LHG,VWH
                HDT:JKM,QZL,BCB
                WSL:KBC,ZGT,TZT,FBH,LTW
                RWH:GLL,HPZ,HMN,PRW,KDC
                FWZ:JKM,DML,FBH
                JVS:GZB,BJC,VQH,JKM,WSB
                ZZJ:RWH,FBH,MZJ,FTV,JGZ
                JCJ:BJS,TZN,RFJ
                PML:PBQ,KBC,VXD
                HMN:QCG,HPZ,BJS,BCB,NNJ
                JGZ:PRW,HDT,GZB,BJS,RWH
                VLK:MPM,LHG,TVZ
                NJR:BVF,PVL,JCJ
                RDJ:JCJ,JGZ,VLK
                ZRP:BFQ,LTW,QCG,SXC,LSG
                VRV:FXS,QZL,BVF,TSN,GDR
                FXS:BVS,VRV,GLL,GSV,GWM
                KDC:HXC,FWZ,LRJ
                DXW:PLG,NCV,QDL
                SXC:TKM,LRJ,MCW
                HPZ:TNG,GSV,TSN
                WPD:TVZ,BCB,HXC,JCJ,VRV
                HWG:MZJ,JCJ,RWH,RPJ,RWH
                FTV:PRW,FZJ,FQM,MPM,PLG
                GDR:DML,TKM,LHK
                VXD:BVF,FXS,BTG,WSL,TBJ
                PLR:SXC,GWM,TVB
                JTM:TZN,HWG,VQH,ZZJ,BJC
                TZN:PDB,PML,LSG,HPZ,PVL
                PRW:HMN,BNZ,GWM,CWD,BCB
            """.trimIndent().split("\n").map {
                val (key, value) = it.split(":")
                key to value.split(",")
            }.toMap()

            var result = input.keys.map { it to mutableMapOf(it to 1L) }.toMap().toMutableMap()
            result.keys.forEach { startingTermite ->
                repeat(20) {
                    result[startingTermite] = result[startingTermite]!!.flatMap { (key, value) ->
                        input[key]!!.map { it to value }
                    }.groupingBy { it.first }.fold(0L) { acc, (_, value) -> acc + value }.toMutableMap()
                }
            }
            val sums = result.map { it.value.values.sum() }
            println(sums.max() - sums.min())
        }
    }
}