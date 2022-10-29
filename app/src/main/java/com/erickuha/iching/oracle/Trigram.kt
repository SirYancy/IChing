package com.erickuha.iching.oracle

enum class Trigram (val trigramNumber: Int){
    HEAVEN(1),
    THUNDER(2),
    WATER(3),
    MOUNTAIN(4),
    EARTH(5),
    WIND(6),
    FLAME(7),
    LAKE(8);

    companion object {
        private val map = Trigram.values().associateBy { it.trigramNumber}
        operator fun get(value: Int) = map[value]
    }
}