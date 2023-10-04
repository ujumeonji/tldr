package run.cd80.tldr.lib.crawler.boj.type

enum class JudgeResult {

    AC,
    WA,
    TLE,
    MLE,
    RE,
    CE,
    OLE,
    SE,
    RF,
    PE,
    QLE,
    UK,
    IE,
    WJ,
    WR,
    NG,
    ;

    companion object {

        fun fromString(value: String): JudgeResult = when (value) {
            "AC", "맞았습니다!!" -> AC
            "WA", "틀렸습니다" -> WA
            "TLE" -> TLE
            "MLE", "메모리 초과" -> MLE
            "RE" -> RE
            "CE" -> CE
            "OLE" -> OLE
            "SE" -> SE
            "RF" -> RF
            "PE" -> PE
            "QLE" -> QLE
            "UK" -> UK
            "IE" -> IE
            "WJ" -> WJ
            "WR" -> WR
            "NG" -> NG
            else -> throw IllegalArgumentException()
        }
    }
}
