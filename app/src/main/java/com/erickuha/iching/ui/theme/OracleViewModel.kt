package com.erickuha.iching.ui.theme

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.erickuha.iching.oracle.Line
import com.erickuha.iching.oracle.Oracle

class OracleViewModel : ViewModel() {

    private val oracle by mutableStateOf(Oracle())

    fun addLine(line: Line){
        oracle.addLine(line)
    }

    private fun reset(){
        oracle.reset()
    }

    fun getLines(): List<Line>{
        return oracle.lines
    }

    fun getPiles(): List<Int>{
        return oracle.piles
    }

    fun divideStalks(index: Int){
        val universe = (-3..3).random()
        var right = (index + universe).coerceIn(5, oracle.piles[0]-5)
        var left = oracle.piles[0]- right

        var remainder = 0
        remainder++
        right--
        remainder += specialModulus(right)
        remainder += specialModulus(left)

        oracle.addPile(remainder)

        if (remainder == 9 || remainder == 8){
            oracle.addDivision(2)
        } else if (remainder == 5 || remainder == 4){
            oracle.addDivision(3)
        } else {
            throw Error("Invalid Remainder - I Ching Oracle is displeased")
        }
        if(oracle.divisions.size == 3){
            var lineNumber = 0
            for (num in oracle.divisions){
                lineNumber += num
            }
            addLine(Line[lineNumber]!!)
            reset()
        }
    }

    private fun specialModulus(number: Int): Int {
        val remainder = number % 4
        return if(remainder == 0) 4 else remainder
    }
}