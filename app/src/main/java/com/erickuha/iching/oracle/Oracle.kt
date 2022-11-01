package com.erickuha.iching.oracle

import android.util.Log

class Oracle {
    private val TAG = "Oracle"

    private var divisions = arrayOf(0, 0, 0)
    var divisionIndex = 0
        private set
    var piles = arrayOf(49,0,0,0)
        private set
    var pilesIndex = 1
        private set
    var lines = arrayOf(
        Line.UNDEFINED,
        Line.UNDEFINED,
        Line.UNDEFINED,
        Line.UNDEFINED,
        Line.UNDEFINED,
        Line.UNDEFINED
    )
        private set
    var currentIndex = 0
        private set
    fun addLine(line: Line){
        this.lines[currentIndex] = line
        this.currentIndex++
    }

    fun reset(){
        this.divisionIndex = 0
        this.divisions = arrayOf(0, 0, 0)
        this.piles = arrayOf(49,0,0,0)
    }

    fun divideStalks(index: Int): Int{
        Log.d(TAG, "$index is passed")
        val universe = (-3..3).random()
        var right = (index + universe).coerceIn(5,this.piles[0]-5)
        var left = this.piles[0]- right


        var remainder = 0
        remainder++
        right--
        remainder += specialModulus(right)
        remainder += specialModulus(left)
        piles[pilesIndex] = remainder
        piles[0] -= remainder
        pilesIndex++
        Log.d("Oracle", "Remainder: $remainder")

        if (remainder == 9 || remainder == 8){
            this.divisions[divisionIndex] = 2
            this.divisionIndex++
        } else if (remainder == 5 || remainder == 4){
            this.divisions[divisionIndex] = 3
            this.divisionIndex++
        } else {
            throw Error("Invalid Remainder - I Ching Oracle is displeased")
        }
        if(divisionIndex == 3){
            var lineNumber = 0
            for (num in divisions){
                lineNumber += num
            }
            lines[currentIndex] = Line[lineNumber]!!
            currentIndex++
            this.reset()
        }
        Log.d("Oracle", "Remainder is $remainder")
        return remainder
    }

    private fun specialModulus(number: Int): Int {
        val remainder = number % 4
        return if(remainder == 0) 4 else remainder
    }

}