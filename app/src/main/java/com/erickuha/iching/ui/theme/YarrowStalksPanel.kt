package com.erickuha.iching.ui.theme

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.erickuha.iching.BuildConfig
import com.erickuha.iching.R
import com.erickuha.iching.Ref

private const val TAG = "Yarrow Stalks"

@Composable
fun YarrowStalks(
    modifier: Modifier = Modifier,
    onYarrowSelected: (Int) -> Unit,
    getYarrowPiles: () -> List<Int>,
){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier.padding(1.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                modifier = modifier.weight(1f),
                painter = painterResource(id = R.drawable.stalk_r),
                contentDescription = null,
            )
        }
        Row(
            modifier.padding(10.dp)
        ) {
            var makeClickable = true
            val piles = getYarrowPiles()
            for(pile in piles){
                Box(
                    Modifier
                        .weight(pile.toFloat())
                        .padding(horizontal = 2.dp)
                ){
                    Row() {
                        for (i in 1..pile) {
                            YarrowStalk(
                                modifier = Modifier.weight(1f),
                                index = i,
                                makeClickable,
                                onYarrowSelected,
                            )
                        }
                        makeClickable = false
                    }
                }
            }
        }
    }
}

@Composable
fun YarrowStalk(
    modifier: Modifier = Modifier,
    index: Int,
    makeClickable: Boolean,
    onYarrowSelected: (Int) -> Unit,
){
    Image(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = makeClickable,
                onClick = {
                    onYarrowSelected(index)
                }
            ),
        painter = painterResource(id = R.drawable.stalk_n),
        contentDescription = null,
        contentScale = ContentScale.FillHeight
    )
}

@Composable
inline fun LogCompositions(tag: String, msg: String){
    if(BuildConfig.DEBUG){
        val ref = remember { Ref(0) }
        SideEffect {
            ref.value++
        }
        Log.d(tag, "Compositions: $msg ${ref.value}")
    }
}
