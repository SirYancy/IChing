package com.erickuha.iching.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.erickuha.iching.R
import com.erickuha.iching.oracle.Hexagram
import com.erickuha.iching.oracle.Line
import com.erickuha.iching.oracle.Trigram

@Composable
fun HexDisplay(
    modifier: Modifier = Modifier,
    lines: List<Line>,
    onReadingComplete: (Hexagram, Hexagram) -> Unit
) {
    Surface() {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (i in (0..lines.lastIndex).reversed()) {
                val lineRes: Int = when (lines[i]) {
                    Line.OLD_YIN -> R.drawable.old_yin
                    Line.OLD_YANG -> R.drawable.old_yang
                    Line.YOUNG_YANG -> R.drawable.young_yang
                    Line.YOUNG_YIN -> R.drawable.young_yin
                    Line.UNDEFINED -> R.drawable.no_line
                }
                Image(
                    painter = painterResource(id = lineRes),
                    contentDescription = null
                )
            }
            if(lines.size == 6){
                val movingLines = mutableListOf<Line>()
                var isMoving = false
                for (line in lines){
                    movingLines.add(Line.getMoved(line))
                    if (line.isOld)
                        isMoving = true
                }
                val hexagram = buildHexagram(lines)
                val movingHexagram = if(isMoving) buildHexagram(lines) else Hexagram.UNDEFINED
                onReadingComplete(hexagram, movingHexagram)
            }
        }
    }
}

fun buildHexagram(lines: List<Line>): Hexagram {
    val lower = getTrigram(listOf(lines[0], lines[1], lines[2]))
    val upper = getTrigram(listOf(lines[3], lines[4], lines[5]))
    return getHexagram(upper, lower)
}

fun getHexagram(upper: Trigram, lower: Trigram): Hexagram {
    val hexNumber = hexTable[lower.trigramNumber][upper.trigramNumber]
    return Hexagram[hexNumber]!!
}

fun getTrigram(lines: List<Line>): Trigram {
    val number = lines[0].binaryValue * 1 +
            lines[1].binaryValue * 2 +
            lines[2].binaryValue * 4
    return Trigram[number]!!
}

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
