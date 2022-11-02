package com.erickuha.iching.oracle

enum class Trigram (val trigramNumber: Int){
    HEAVEN(7),
    THUNDER(6),
    WATER(5),
    MOUNTAIN(4),
    EARTH(3),
    WIND(2),
    FLAME(2),
    LAKE(1);

    companion object {
        private val map = Trigram.values().associateBy { it.trigramNumber}
        operator fun get(value: Int) = map[value]
    }
}