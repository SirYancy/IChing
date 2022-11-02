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
}