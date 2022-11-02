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
        }
    }
}
