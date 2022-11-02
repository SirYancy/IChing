package com.erickuha.iching.oracle

import androidx.compose.runtime.*

class Oracle {
    var divisions: List<Int> by mutableStateOf(emptyList<Int>())
    var piles: MutableList<Int> = mutableStateListOf(49)
    var lines: List<Line> by mutableStateOf(emptyList<Line>())

    fun addLine(line: Line){
        lines = lines + line
    }

    fun addDivision(index: Int){
        divisions = divisions + index
    }

    fun addPile(newPile: Int){
        piles.add(newPile)
        piles[0] = piles[0] - newPile
    }

    fun reset(){
        divisions = emptyList()
        piles = mutableStateListOf(49)
    }

    companion object {
        val hexTable = arrayOf(
            arrayOf(2, 23, 8, 20, 16, 35, 45, 12),
            arrayOf(15, 52, 39, 53, 62, 56, 31, 33),
            arrayOf(7, 4, 29, 59, 40, 64, 47, 6),
            arrayOf(46, 18, 48, 57, 32, 50, 28, 44),
            arrayOf(24, 27, 3, 42, 51, 21, 17, 25),
            arrayOf(36, 22, 63, 37, 55, 30, 49, 13),
            arrayOf(19, 41, 60, 61, 54, 38, 58, 10),
            arrayOf(11, 26, 5, 9, 34, 14, 43, 1),
        )
    }


}