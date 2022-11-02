package com.erickuha.iching.oracle

enum class Line(val number: Int, val isOld: Boolean, val binaryValue: Int){
    UNDEFINED(0, false, -1),
    OLD_YIN(6, true, 0),
    YOUNG_YANG(7, false, 1),
    YOUNG_YIN(8, false, 0),
    OLD_YANG(9, true, 1);

    companion object {
        private val map = Line.values().associateBy { it.number}
        operator fun get(value: Int) = map[value]
        fun getMoved(line: Line): Line{
            return when(line){
                OLD_YIN -> YOUNG_YANG
                OLD_YANG -> YOUNG_YIN
                else -> line
            }
        }
    }
}
