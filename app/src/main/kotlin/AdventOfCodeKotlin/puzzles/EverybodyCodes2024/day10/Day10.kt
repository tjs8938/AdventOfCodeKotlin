package year2024.day10

fun main() {
    Day10.part1()
    Day10.part2()
    Day10.part3()
    Day10.part3b()
}

class Day10 {
    companion object {
        fun part1() {
            val input = """
                **SXKZ**
                **FRGW**
                TM....HF
                RG....JZ
                SW....LK
                XD....CV
                **JHMD**
                **CLVT**
            """.trimIndent().split("\n")

            val result = StringBuilder()
            (2..5).forEach { row ->
                (2..5).forEach { col ->
                    result.append(input.map { it[col] }.filterNot { it == '.' }
                        .intersect(input[row].filterNot { it == '.' }.toSet()).first())
                }
            }
            println(result)
        }

        fun part2() {
            val input = """
                **TFCB** **LGBR** **QLJC** **RVWK** **LHMQ** **DBSN** **PSLX** **MDLC** **FNKC** **BSKC** **GLCX** **NPFX** **KCNJ** **QPMC** **GRLF**
                **WQPZ** **QKVP** **MHTN** **LJDG** **XDRK** **LXVR** **GKWB** **VHRX** **DWRB** **JFTD** **PZQV** **BLZK** **ZVGS** **JXLT** **MVDN**
                CD....HK SF....LV GF....VR FD....XV HK....XM LX....GQ NQ....TZ MX....RJ CW....VG CJ....VW MZ....KR FB....GC NF....RZ XQ....TF FQ....XT
                BG....XS KB....ZR JQ....KC PM....ZT SN....LC RS....TH MP....CF HT....LW LT....BR GT....XF SL....CX KL....SZ DS....QB WG....SR VG....LC
                FZ....PT MJ....WX ZN....XH WJ....RG VP....QJ KD....ZC KX....LH CD....ZF FK....HP KS....DR QG....VN DM....TH VW....PH LN....CZ SK....ZW
                QW....MR HP....QG BL....TM BL....KQ GZ....DR BJ....VN GW....SB NV....GS DJ....XN LB....NQ HP....BJ NW....PX JC....GK JV....PM MR....ND
                **KXGM** **XWHF** **XKZB** **FQPT** **GCNS** **JHKG** **HQMC** **ZGST** **VLJP** **RVNQ** **KBRH** **MCWG** **QRWF** **ZWFR** **WSKC**
                **SHRD** **ZSJM** **VGFR** **ZMBX** **VPJZ** **TCQZ** **NFZT** **WJFN** **THGX** **GLWX** **SNJM** **STDH** **HBDP** **SNGV** **QXTZ**

                **KCTG** **TMCN** **BVGK** **SXCW** **CXDT** **JVMD** **QDHV** **PDZT** **ZFRP** **WQXL** **RHBW** **PCVW** **GQJC** **HVLP** **DJTK**
                **XSQZ** **FKWD** **TLMN** **RVLF** **QSBV** **NGXS** **FBPW** **CLRN** **HMJV** **KVZJ** **LNMC** **JTGK** **LPRX** **ZWJD** **ZWLF**
                LP....FD LN....BV QW....FR HX....CN KJ....CX JD....TV QV....MP ML....NZ RC....KV WV....XH KT....PD LB....WH HK....RC SB....TD TQ....HW
                WR....XV FC....MD BG....SX PB....ZJ MS....BL KS....PG SC....NR DC....WQ BM....SP NJ....QD QG....ML QC....FP LZ....PX KH....RW JF....GC
                GH....QC SG....JK ML....NC SV....TW QR....TD RC....XB DG....JX HJ....TV JN....HG FK....BM WX....HB VK....RT DJ....FS JV....FX LD....MP
                KS....ZT WZ....TP VT....DK DL....RF HV....ZP NW....FM BF....WH PS....XR TQ....FZ CP....LZ NR....CF XG....SJ MT....GQ ZM....LP VB....ZK
                **HLFD** **GLBS** **CFWQ** **NPBH** **LRZJ** **KWPR** **GSJC** **JXQS** **CNQT** **DCNF** **KGFQ** **HSFQ** **FHZD** **XMRK** **HMBQ**
                **PVRW** **VZJP** **RXDS** **JDTZ** **PHKM** **BCTF** **RMNX** **MVHW** **BKSG** **PBMH** **XTPD** **RBLX** **KSTM** **STBF** **CVGP**

                **BVHF** **SVFL** **NLVH** **HFTM** **PJKW** **ZVJK** **LCFT** **MDVQ** **PQSG** **TRDM** **BRTD** **NSBZ** **PBSQ** **LBFZ** **HNVB**
                **JKNX** **THKJ** **SBQM** **KJLS** **LDQC** **BLHG** **DSRB** **ZWLS** **HRFC** **WFXJ** **LCSP** **LHWQ** **JHKD** **HXGV** **QCJF**
                RF....JN JS....VK TM....RN ZC....DP WT....XM LB....QH BN....VZ SZ....WR PQ....GS NK....MF CP....TL RQ....JP RF....GX PM....LB TQ....BD
                TG....QB LR....BX KP....ZQ NJ....HQ RV....CP VP....JS XF....WH HD....VQ MZ....BL TZ....GP DS....GZ ZC....LF BZ....MD CG....DZ NX....WK
                MV....HS FH....CT XL....JC SF....LW DB....KH ZD....GX SQ....MD NF....JK FR....WX HV....XQ QJ....HN GM....SB HK....PT SH....FJ JF....MC
                XD....LK WD....MP HV....SB KG....TM SJ....QL WK....TF CR....LT BM....PL HD....NC WR....DJ KF....BR WH....NT JS....QL NW....VX VG....HL
                **MGTS** **XBDC** **XZCJ** **WGNZ** **XVTB** **SDXQ** **WXMH** **KPJB** **MDNL** **KHGP** **ZHQJ** **GFRM** **ZLMT** **WDPJ** **MGTL**
                **DRLQ** **MWRP** **KTRP** **DPCQ** **HMRS** **FTWP** **VNZQ** **HFRN** **WZBX** **VZNQ** **NGKF** **JPCT** **GFRX** **CSNM** **WDKX**

                **XSFR** **BRKS** **GWNS** **TGXS** **LCRQ** **CSRJ** **PNLW** **RPNJ** **ZFPL** **HGXQ** **CKBM** **ZPML** **HFSB** **NTRP** **ZTHS**
                **HGLK** **DHCP** **DQJM** **PJCB** **VKNW** **BFXT** **VRJQ** **FKXT** **VQWS** **BKML** **QJWF** **RSFN** **DWJX** **XWZB** **FNRC**
                XV....KP VC....PW WJ....PS MC....FQ PZ....SJ RW....JP WL....RF FW....HN WS....QV CL....GX MV....WR HS....MR DX....MJ ZP....TN WC....NK
                FT....CG LM....KB LV....HX XS....DH TR....WK MD....SQ CK....BS BJ....PG GB....JT DT....WB KN....HQ JT....BW HR....FS KW....BC LJ....ZS
                BH....JS RQ....XS NR....GM LP....RJ FN....DL KF....CL QT....NV KR....CX ML....PZ RJ....QN BJ....FC LV....ZP GW....TP FR....MH FR....TB
                RD....LM HD....TZ KD....QT GB....TW QV....CG XG....TB PJ....GH TM....VD RK....FH HK....MP SL....DZ NF....KQ ZQ....NB SL....QX XH....QM
                **MPBJ** **VWXZ** **VLHK** **MDLQ** **ZPSJ** **WPKL** **CKBG** **VHMW** **RGHK** **CNTP** **VDNH** **KWVJ** **QMTP** **KSCM** **WXJM**
                **CDVT** **QMTL** **PRTX** **HFWR** **TFGD** **QGMD** **FHTS** **GDBC** **BMJT** **RDJW** **SRLZ** **TQBH** **GNZR** **FHQL** **QLKB**

                **FZTC** **CWQD** **ZKLV** **LXPF** **JKLW** **DGCL** **BWPS** **KDNP** **PLJZ** **KWGQ** **WFPJ** **DSVP** **BHKV** **HQZB** **NGMS**
                **GSKJ** **VGKZ** **BNRS** **SQWH** **RFQV** **FPNR** **XFTD** **HMWS** **DKST** **CJXV** **CQVG** **WLQZ** **DZXS** **XGLT** **PZBR**
                ST....JR QD....BJ XZ....RH JN....LV MT....NC NB....QZ RM....BJ SZ....MK KJ....VP MT....RG WH....ML JP....FD BS....NW GP....BM QJ....CL
                WK....XP TX....ZL DB....CW BS....WK VK....BQ DP....CR NP....XW HW....PG HN....LZ XJ....LK FG....KT WN....BS MF....GP CV....NF NK....GT
                QG....NC CG....RK KV....LG ZQ....FP DX....FR FG....TW CL....KD TD....QN ST....DC WQ....CN VS....RZ GH....VR ZD....VK ZQ....XT BZ....PS
                FM....BZ FP....VW MQ....SN HX....TG LW....JP KL....SH TF....HS RV....LB WR....QX SD....PV PJ....QC LZ....QC CX....HT HL....KD MV....RD
                **WPBM** **TXPR** **QWDC** **TVGN** **BPNC** **BQWT** **HLJN** **QGZL** **WCNV** **MTNL** **TZKS** **CJFB** **GWFP** **CNFV** **DVKC**
                **RQNX** **BJLF** **GXMH** **ZBJK** **MTDX** **KHSZ** **CRKM** **VRBT** **HXQR** **PSDR** **RLHM** **HRNG** **CMNT** **MDPK** **QLJT**

                **JRTZ** **MJQD** **BPMR** **QHBN** **FLSM** **TDMQ** **QJVM** **JRBW** **TCRZ** **KTVN** **JDTS** **BTHC** **GPCV** **DPNX** **VNBM**
                **MGLQ** **NFLS** **XDGT** **JXVM** **HRTZ** **BFNR** **ZDNX** **KPTX** **SLWJ** **RJZS** **WNZB** **KLGS** **QNDM** **TKWR** **PLQC**
                XZ....GH BS....NZ ML....TB TJ....ZK SL....HZ QF....NZ ZV....JR TR....JH JX....VL CM....LP NJ....QP NL....VP QV....LH TC....XB QN....VM
                QC....BT GP....CF FP....XC VP....QS KW....PV WC....HT NM....DQ QG....LC MC....KS JR....NX XV....DM JZ....KQ SF....WJ NH....ZK WX....TC
                WV....ML KD....JL WZ....GR ML....CX MB....QF JX....BM HC....BT MN....WZ WZ....GH TZ....KS ZB....KF RH....MC BD....PM DV....JP RD....JS
                NJ....SR MH....WQ SN....QD BN....DH XR....TC GR....DV FX....WK KX....BP TQ....RB WV....GF WS....TL TS....GB GC....NZ FL....RW BL....HP
                **XWHV** **KZGP** **WZNC** **LKZS** **VBWK** **GJCX** **HCBT** **GLZQ** **GHVK** **FPCM** **KFMQ** **NZQV** **WSFZ** **FBCZ** **WXTH**
                **CBSN** **CHBW** **QLFS** **DPCT** **CPQX** **ZWVH** **KFWR** **NMCH** **XBMQ** **LGXW** **VLPX** **RMPJ** **BLHJ** **HLJV** **DRSJ**

                **XVGW** **DNKZ** **MXRS** **WNBP** **HNRL** **VHQW** **KSDP** **QKFS** **KLRQ** **QXBT** **KVGM** **TPDV** **GPJF** **VLCK** **CDMF**
                **NSDR** **BCLX** **CVNK** **DVGK** **PWZD** **MNXP** **XVLW** **JRLX** **DBCJ** **LWDG** **CRZP** **XMKN** **CNLR** **NPWT** **QHJZ**
                DX....WV GS....NJ QB....ZG XW....KJ TX....FD GZ....FP TL....JC SK....VQ ML....DH RG....WL SC....VM MD....BV HV....ZL NL....BM QK....WZ
                FQ....CL RZ....TL SX....MJ NB....DL BZ....PS KC....WM WQ....VF DM....JF FQ....RG FV....BZ TG....BQ XW....PC FQ....BP VQ....HW NM....HV
                HB....NS KD....HC LK....VR RP....TV HL....WK LQ....HV ZP....XN LH....NT NP....XK NX....TD JW....XZ HQ....KS GS....ND KP....CF GC....SD
                MR....GZ XP....BF DT....CN GF....HQ MN....GR XN....TB DK....SM RP....XB CW....BJ KC....MQ PN....KR ZN....TJ KR....CJ DT....GS XF....PJ
                **ZQLB** **TRSJ** **ZGJB** **RJXL** **FBTS** **FCZT** **TJQC** **TMBD** **WXNM** **NVMF** **JWNX** **BJZC** **HKQS** **DQSB** **PKGN**
                **CMHF** **GPFH** **LDQT** **HFTQ** **GXKM** **BGKL** **FNZM** **PNVH** **GFHP** **ZCRK** **QTSB** **QSWH** **BVDZ** **FGMH** **VXWS**
            """.trimIndent().split("\n\n").flatMap {
                val splits = it.split("\n").map { it.split(" ") }
                (0 until splits[0].size).map { i -> splits.map { it[i] } }
            }

            println(input.sumOf { grid ->
                val result = StringBuilder()
                (2..5).forEach { row ->
                    (2..5).forEach { col ->
                        result.append(grid.map { it[col] }.filterNot { it == '.' }
                            .intersect(grid[row].filterNot { it == '.' }.toSet()).first())
                    }
                }
                result.toString().mapIndexed { index, c ->
                    (index + 1L) * (c.code - 'A'.code + 1)
                }.sum()
            })
        }

        val input = """
                **R?Q?**JFKN**XLDM**PKQC**VGZW**SRQK**LHNZ**XSKB**CHPV**RQGW**TMQK**VFBK**PNVL**JDQG**HMBN**LNGS**DJRT**SWDF**QNLR**GPZT**
                **LZ?K**PDSZ**BTWK**HFXT**BKTH**LMJN**PSCW**RDPW**MNSZ**BLTJ**ZHXC**JRXH**WXCD**CTBS**ZTCF**BPZW**VXHK**GCVP**MHVC**LVXC**
                CF....WR....PF....X?....RF....?Z....SR....P?....JD....?Z....?R....CN....J?....XP....SQ....?F....SG....V?....P?....MR....LT
                QX....ZM....CK....?S....?P....JT....K?....HC....?X....NT....KL....P?....F?....TN....BR....?N....?Z....FX....?L....ZH....SC
                KV....ST....DH....?G....H?....GN....?X....NZ....?B....HC....?J....HT....D?....CM....ZD....L?....?P....MH....?S....FV....XB
                BD....LG....NJ....T?....DQ....?B....LQ....?G....?V....WP....GV....X?....QR....?L....G?....HM....WB....?T....DG....Q?....PG
                **SBDC**TGWH**HGFJ**LMGR**PRQJ**FTXV**XMTK**VNHJ**WKDT**NPMK**NRPV**DNCP**FQRK**RWLZ**VLQJ**TFCJ**BGSP**JRLH**WPJD**HSNQ**
                **?VFM**CMLR**NCSP**DWBS**NDCF**GBZW**FGQR**TGZC**XJBR**VHCZ**LGJB**ZTMQ**MBJT**XPMN**SRGD**DMHV**MWZF**XKMT**FSGZ**BRMF**
                RH....DB....YJ....G?....WP....C?....DY....YW....P?....CS....?J....RY....TD....YD....LK....C?....FH....YL....QZ....BH....ZY
                GV....JS....P?....KZ....Z?....YL....RJ....MK....YW....?F....SD....CN....PK....V?....RW....YX....PY....KH....H?....RV....?B
                XP....LC....BT....VB....NY....FX....?Z....GN....MT....LY....PY....SH....YR....RJ....JV....JL....?R....?Z....TY....J?....MC
                TN....MF....LM....YR....TD....PK....QF....J?....NS....XJ....XK....K?....?Z....XC....Y?....KF....ZW....PG....XK....YS....PQ
                **H?JG**BDKQ**CNRT**KLNX**MXFP**SKML**QBXR**NKQW**VFBM**JKHM**CVGZ**KHXS**FZRD**NKVP**TRZK**QXZD**JSXD**DBPC**RJCL**QGTP**
                **?N?R**LNSZ**FGPM**DWBP**SJKT**VWHD**FMZS**RZGD**KLPD**XCFS**WNQB**DTLG**CWKQ**BDLM**WJDF**PLHK**ZLVR**XQLK**TKHX**RXZK**
                MF....NP....BM....P?....B?....JS....K?....ZJ....W?....LB....SX....?Z....KH....?W....DX....?S....D?....JC....D?....TC....BQ
                KV....HJ....S?....CT....X?....FZ....L?....QX....KC....M?....N?....WL....TN....?Z....BK....L?....HN....L?....N?....RW....FG
                TZ....WG....ZK....F?....LK....P?....H?....SF....DR....?T....RK....?G....?X....CQ....?M....JT....PX....W?....?X....JV....ZP
                RS....LC....RQ....N?....WR....T?....VR....?M....G?....VP....?J....FB....DS....?P....GV....?Z....KG....?S....QP....?L....XK
                **CKSM**GWCP**QSZH**VJFR**LRVB**FPBR**KLDJ**JMHX**HCQN**NQBP**SFLR**JVZN**HJXL**WRZX**BGLV**NSTJ**GWCK**VNWS**BDNW**VJFB**
                **FZW?**HRMJ**JBDK**HTCG**ZCWD**CTZJ**PHVW**CFSP**TGRW**TLVR**KXJH**WFBQ**PSTN**GCQF**SXNM**WGFR**PQNH**ZJGR**QPVG**HCLW**
                LX....MZ....TP....JB....M?....ZW....Y?....CF....YN....TQ....TW....FT....RQ....BJ....YJ....J?....RC....PG....?K....XH....SW
                WJ....FN....WM....HQ....HP....JY....DG....MR....?J....HW....V?....W?....XG....FC....?X....MD....LX....?N....YB....?R....DC
                VD....SQ....KN....XD....TK....LV....TR....YH....VH....?P....FY....YC....?Y....YW....KL....KY....D?....CX....WF....QY....?Q
                TG....KC....ZR....SG....ZY....P?....VS....S?....KZ....YF....JX....QJ....WH....?T....GC....WN....YS....YJ....NH....GB....YJ
                **?Q?T**DZLN**RNTG**PBFW**ZRGF**DJHB**BNKT**JCPB**TBMV**NBMK**SGLZ**CMBF**XRVW**NMQF**ZRHN**FVCB**NXQR**CLFG**MVDT**LSPG**
                **D?L?**V?YW**XWMP**DKHL**THVS**MCKW**LGVX**RTKN**PSGZ**TRQJ**PJCW**DPHS**KSNH**BZHD**KJPQ**ZJQL**DTPF**ZJKH**BNXZ**NCBV**
                LV....FJ....?V....WX....MP....?R....DJ....?L....PQ....?L....KR....Z?....?J....VR....BD....R?....S?....TD....FL....?X....PG
                RH....XQ....B?....RZ....F?....ZQ....?M....VW....JK....?V....C?....TS....B?....SZ....QC....K?....?F....XH....C?....NH....JV
                KB....ND....?P....HT....L?....SW....?P....NS....C?....GS....?N....LX....HC....X?....FH....?T....L?....ZR....?J....SD....LR
                TC....GP....SJ....G?....?D....HG....CF....X?....R?....TX....?Q....GW....M?....WN....LM....?P....?M....NP....K?....MT....BC
                **PCRH**SYCZ**SHVZ**TVMG**DWQK**PZRF**FDJW**HGXQ**JLXR**CXZF**QTFB**JLGW**JDBF**CXWL**LTDM**TNKP**ZCHV**PXBT**SFCH**TZRD**
                **VBKF**BGK?**DBJL**RXNZ**LBPM**GSQT**SMCP**LVSW**HKQC**LSGV**NKRX**TKZX**MCGZ**GVRS**FCGB**MGRS**BLSM**DNRV**LKGJ**XJHM**
                DG....FC....FX....SL....MF....PG....DL....?B....YQ....YW....LY....JG....TD....XM....LM....JV....Y?....YN....XL....VQ....R?
                KL....BX....DW....HP....JZ....LK....RM....DZ....NM....MR....K?....YC....GN....RY....JH....Y?....VK....SR....KN....Y?....CG
                PQ....RT....BN....JM....XT....DB....?W....RW....?H....V?....GJ....Q?....P?....CF....X?....FT....CS....?V....?Y....PB....TB
                NZ....VH....RK....ZV....NQ....WH....PY....PY....XP....ST....QN....ZR....XY....?L....SY....WD....QZ....GB....ZJ....ZH....XY
                **XQ??**GV?F**MKFP**?PBS**NFXH**PWSN**VLGX**DSXL**MGQR**ZJHB**ZQFN**DJFQ**VQCR**CXDV**CGMV**RBZP**MVWK**QXRV**VPXD**RSWZ**
                **D?GL**WZYC**RXWN**YQRJ**GJZT**DGCK**BTDM**PHJZ**FNDZ**XWGT**CXKB**LGHZ**PWJT**THLZ**XJKN**TCHF**DSGR**WNPZ**WGHT**PTJQ**
                LK....GB....?Z....FX....MS....?T....?S....FG....DS....G?....DT....?N....?L....WC....JZ....P?....WB....S?....Q?....VB....ZJ
                WS....TQ....J?....KH....NW....?L....C?....BX....PK....?N....HX....?S....JX....?R....H?....VK....F?....KM....N?....HT....WF
                JP....ND....DP....?R....JB....?P....JW....V?....?J....RM....ZW....?P....HT....V?....XF....S?....RH....G?....?J....PD....KS
                RZ....FX....?C....WN....CQ....?X....D?....QT....HZ....B?....VJ....?K....ZD....Q?....TD....C?....ZQ....D?....W?....GM....QR
                **WRPJ**YM?L**ZVBH**BS?M**PSWB**FXHJ**FCNJ**QBTK**SHKL**DNMF**SDWP**TPKC**XBDZ**FPJB**PDTS**WSVG**PHTL**GJFL**ZRJB**BGMK**
                **?BSF**RPXV**CDJG**HWYK**CQML**MLZT**KSWQ**VWGF**BWJP**QLVR**VJTH**SBNX**LHMG**RWMQ**LHFZ**NKMQ**BFZQ**KDSM**NQMF**FVDH**
                WG....PT....WJ....DK....TZ....BP....SG....WZ....PS....YZ....?Y....PB....JP....MY....WJ....YC....Y?....LV....X?....MT....XY
                SD....JZ....ST....VC....LX....MV....VH....JN....LD....?G....SN....RL....?X....LF....?P....XZ....NV....ZW....VQ....YV....?V
                HQ....FR....QR....ZH....WR....SF....XD....KQ....?C....LB....LC....VZ....QF....BT....XY....QT....RT....M?....MG....KQ....PF
                LV....KB....GL....BP....GK....QC....LF....CM....YK....DV....HP....Y?....YM....D?....QL....?W....WF....KY....DY....?L....HT
                **ZTHL**DW?R**PLST**?VGB**VTGK**?MDK**HVDG**WSNT**QDXW**NBLJ**CHZR**TMXJ**WKBP**MVWS**PQTJ**TLPS**PHGC**WRZF**SZPH**WRBK**
                **Q?VG**CNBY**WRQK**FXYZ**RXZF**QYHN**ZXLM**RPVM**VHSL**WFSV**QTDN**PFCL**CHVQ**FTZL**HKCV**FVZJ**JNRV**SBQN**DNTL**MCVQ**
                WJ....QG....TV....?F....BH....?K....ZN....?C....NK....Q?....NJ....?K....?T....WK....?R....HQ....S?....HJ....ZR....D?....QF
                TM....NV....ND....?K....QJ....R?....?B....LG....WP....V?....T?....LD....?V....HG....TZ....J?....ZR....W?....BK....P?....KB
                LS....DC....HG....Q?....SV....N?....KR....V?....SB....C?....F?....CZ....MN....?C....FV....P?....VX....?D....?Q....TH....VR
                PR....HZ....PX....?L....GW....T?....?W....HJ....T?....XH....B?....RQ....P?....SB....?X....CK....TL....C?....XS....C?....WG
                **CJ?N**WYCJ**HDXG**YZGJ**DQBN**WCZ?**FCNW**KCGB**BCMK**CDQH**SBJL**VKHN**XFNT**KBCQ**ZWRL**RHCX**XZSW**JCXH**KCXB**NFDH**
                **?PS?**?SQP**NVFZ**?QPS**HSJW**RMYG**RBJK**DHJL**RPNT**TMXG**KFWG**ZDRQ**SMJG**HRXG**MFBX**BWQK**TDLF**GDPK**FQMR**TPGL**
                TH....CJ....GP....TF....RF....HQ....KD....JS....SG....MK....GC....FT....HW....SG....XJ....WN....CG....XD....LP....FQ....BY
                SX....NQ....CX....MD....MZ....BS....ZP....RW....QB....TX....DP....BW....BN....TP....CG....FZ....WR....HL....CH....BJ....CG
                KR....PV....KQ....HV....GJ....TN....HQ....CF....PZ....RW....RN....SJ....MK....JQ....HQ....RM....VS....BZ....RN....DX....T?
                ZL....MW....BJ....NZ....WP....DX....NM....BX....HJ....CN....XK....ZL....DZ....XF....BD....LS....MP....FT....TV....KM....XK
                **QLVT**KQSR**CJQM**MYBN**MTXZ**KNXD**SDHX**HYXS**JXQZ**QTY?**TXCD**?GRX**QPZD**YCLG**SQNH**QX?G**HPGB**WLDN**NVPT**RGVP**
                **KXHZ**YNP?**TPBK**RH?G**GPFR**?ZQY**MZPQ**NB?K**GSWH**PXZR**PNZR**HWYN**BWHK**WVH?**JDCG**YCNT**VRMC**SY?C**JDHL**YJ?X**
            """.trimIndent().split("\n").map { it.toList().toMutableList() }.toMutableList()

        class Platinum(val row: Int, val col: Int, val input: MutableList<MutableList<Char>>) {

            val neighbors = mutableSetOf<Platinum>()

            fun row(y: Int) = Companion.input[row * 6 + y].subList(col * 6, col * 6 + 8)
            fun col(x: Int) = Companion.input.subList(row * 6, row * 6 + 8).map { it[col * 6 + x] }
            operator fun get(pair: Pair<Int, Int>): Char = input[row * 6 + pair.first][col * 6 + pair.second]
            operator fun set(pair: Pair<Int, Int>, value: Char) {
                input[row * 6 + pair.first][col * 6 + pair.second] = value
            }

            fun runic(): String {
                val result = StringBuilder()
                (2..5).forEach { row ->
                    (2..5).forEach { col ->
                        result.append(this[row to col])
                    }
                }
                return result.toString()
            }


        }

        fun part3b() {

            val COLUMNS = (input[0].size - 2) / 6
            val ROWS = (input.size - 2) / 6

            val complete = mutableSetOf<Platinum>()
            val toProcess = mutableListOf<Platinum>()
            val allPlatinum = mutableMapOf<Pair<Int, Int>, Platinum>()
            (0 until ROWS).forEach { row ->
                (0 until COLUMNS).forEach { col ->
                    val thisPlatinum = Platinum(row, col, input)
                    allPlatinum[row to col] = thisPlatinum
                    toProcess.add(allPlatinum[row to col]!!)
                    allPlatinum[row - 1 to col]?.let {
                        thisPlatinum.neighbors.add(it)
                        it.neighbors.add(thisPlatinum)
                    }
                    allPlatinum[row to col - 1]?.let {
                        thisPlatinum.neighbors.add(it)
                        it.neighbors.add(thisPlatinum)
                    }
                }
            }

            while (toProcess.isNotEmpty()) {
                val platinum = toProcess.removeAt(0)

                var modified = false
                var empty = 0
                (2..5).forEach { row ->
                    (2..5).forEach { col ->
                        val innerRow = platinum.row(row)
                        val innerCol = platinum.col(col)

                        if (platinum[row to col] == '.') {
                            empty++

                            val match = innerRow.filter { it != '?' && it != '.' }
                                .intersect(innerCol.filter { it != '?' && it != '.' }.toSet())

                            if (match.size == 1) {
                                platinum[row to col] = match.first()
                                modified = true
                                empty--
                            } else if (innerRow.count { it == '?' } + innerCol.count { it == '?' } == 1) {
                                val missingRow = setOf(innerRow[0], innerRow[1], innerRow[6], innerRow[7]).minus(
                                    setOf(
                                        innerRow[2],
                                        innerRow[3],
                                        innerRow[4],
                                        innerRow[5]
                                    )
                                )
                                val missingCol = setOf(innerCol[0], innerCol[1], innerCol[6], innerCol[7]).minus(
                                    setOf(
                                        innerCol[2],
                                        innerCol[3],
                                        innerCol[4],
                                        innerCol[5]
                                    )
                                )
                                if (missingCol.size == 1 && missingRow.size == 1) {
                                    if (missingCol.first() == '?' && missingRow.first() != '?') {
                                        platinum[row to col] = missingRow.first()
                                        innerCol.indexOf('?').let {
                                            platinum[it to col] = missingRow.first()
                                        }
                                        modified = true
                                        empty--
                                    } else if (missingCol.first() != '?' && missingRow.first() == '?') {
                                        platinum[row to col] = missingCol.first()
                                        innerRow.indexOf('?').let {
                                            platinum[row to it] = missingCol.first()
                                        }
                                        modified = true
                                        empty--
                                    }
                                }
                            }
                        }
                    }
                }
                if (empty == 0) {
                    complete.add(platinum)
                }
                if (modified) {
                    platinum.neighbors.filter { it !in complete && it !in toProcess }.forEach {
                        toProcess.add(it)
                    }
                }
            }

            println(complete.sumOf { platinum ->
                platinum.runic().mapIndexed { index, c ->
                    (index + 1L) * (c.code - 'A'.code + 1)
                }.sum()

            })
        }

        fun part3() {

            val COLUMNS = (input[0].size - 2) / 6
            val ROWS = (input.size - 2) / 6

            val complete = mutableSetOf<Pair<Int, Int>>()
            val toProcess =
                (0 until ROWS).flatMap { row -> (0 until COLUMNS).map { col -> row to col } }.toMutableList()

            while (toProcess.isNotEmpty()) {
                val (outerRow, outerColumn) = toProcess.removeAt(0)

                var modified = false
                var empty = 0
                (2..5).forEach { row ->
                    (2..5).forEach { col ->
                        val innerRow = input[outerRow * 6 + row].subList(outerColumn * 6, outerColumn * 6 + 8)
                        val innerCol = input.subList(outerRow * 6, outerRow * 6 + 8).map { it[outerColumn * 6 + col] }

                        if (input[outerRow * 6 + row][outerColumn * 6 + col] == '.') {
                            empty++

                            val match = innerRow.filter { it != '?' && it != '.' }
                                .intersect(innerCol.filter { it != '?' && it != '.' }.toSet())

                            if (match.size == 1) {
                                input[outerRow * 6 + row][outerColumn * 6 + col] = match.first()
                                modified = true
                                empty--
                            } else if (innerRow.count { it == '?' } + innerCol.count { it == '?' } == 1) {
                                val missingRow = setOf(innerRow[0], innerRow[1], innerRow[6], innerRow[7]).minus(
                                    setOf(
                                        innerRow[2],
                                        innerRow[3],
                                        innerRow[4],
                                        innerRow[5]
                                    )
                                )
                                val missingCol = setOf(innerCol[0], innerCol[1], innerCol[6], innerCol[7]).minus(
                                    setOf(
                                        innerCol[2],
                                        innerCol[3],
                                        innerCol[4],
                                        innerCol[5]
                                    )
                                )
                                if (missingCol.size == 1 && missingRow.size == 1) {
                                    if (missingCol.first() == '?' && missingRow.first() != '?') {
                                        input[outerRow * 6 + row][outerColumn * 6 + col] = missingRow.first()
                                        innerCol.indexOf('?').let {
                                            input[outerRow * 6 + it][outerColumn * 6 + col] = missingRow.first()
                                        }
                                        modified = true
                                        empty--
                                    } else if (missingCol.first() != '?' && missingRow.first() == '?') {
                                        input[outerRow * 6 + row][outerColumn * 6 + col] = missingCol.first()
                                        innerRow.indexOf('?').let {
                                            input[outerRow * 6 + row][outerColumn * 6 + it] = missingCol.first()
                                        }
                                        modified = true
                                        empty--
                                    }
                                }
                            }
                        }
                    }
                }
                if (empty == 0) {
                    complete.add(outerRow to outerColumn)
                }
                if (modified) {

                    listOf(
                        outerRow - 1 to outerColumn,
                        outerRow + 1 to outerColumn,
                        outerRow to outerColumn - 1,
                        outerRow to outerColumn + 1
                    ).filter { it.first in 0 until ROWS && it.second in 0 until COLUMNS }
                        .filter { it !in complete && it !in toProcess }.forEach {
                            toProcess.add(it)
                        }

                }
            }

            println(complete.sumOf { (outerRow, outerColumn) ->
                val result = StringBuilder()
                (2..5).forEach { row ->
                    (2..5).forEach { col ->
                        result.append(input[outerRow * 6 + row][outerColumn * 6 + col])
                    }
                }
                result.toString().mapIndexed { index, c ->
                    (index + 1L) * (c.code - 'A'.code + 1)
                }.sum()

            })
        }
    }
}