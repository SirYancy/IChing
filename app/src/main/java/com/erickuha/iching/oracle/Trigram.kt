package com.erickuha.iching.oracle

enum class Trigram (val trigramNumber: Int){
    HEAVEN(7),
    LAKE(6),
    FLAME(5),
    THUNDER(4),
    WIND(3),
    WATER(2),
    MOUNTAIN(1),
    EARTH(0);

    companion object {
        private val map = Trigram.values().associateBy { it.trigramNumber}
        operator fun get(value: Int) = map[value]
    }
}