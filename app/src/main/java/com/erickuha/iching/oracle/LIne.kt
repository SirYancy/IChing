package com.erickuha.iching.oracle

enum class Line(val number: Int){
    UNDEFINED(0),
    OLD_YIN(6),
    YOUNG_YANG(7),
    YOUNG_YIN(8),
    OLD_YANG(9);

    companion object {
        private val map = Line.values().associateBy { it.number}
        operator fun get(value: Int) = map[value]
    }
}
