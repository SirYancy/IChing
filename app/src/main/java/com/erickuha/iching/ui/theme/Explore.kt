package com.erickuha.iching.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erickuha.iching.Home
import com.erickuha.iching.oracle.Hexagram


@Composable
fun ExploreOracleGrid(
    modifier: Modifier = Modifier
){
    val hexList = Hexagram.values().toMutableList()
    hexList.removeFirst()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 90.dp),
        content = {
            items(hexList.size){
                index ->
                HexGridItem(modifier = Modifier,
                    hexagram = hexList[index])
            }
        }

    )
}

@Composable
fun HexGridItem(
    modifier: Modifier,
    hexagram: Hexagram
){
    val hexTextArray = stringArrayResource(id = hexagram!!.resId)
    Card(
        colors = CardDefaults.cardColors(),
        elevation =
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = hexTextArray[0],
                fontSize = 8.sp
            )
            Text(
                text = hexTextArray[2],
                fontSize = 50.sp
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 320,
    heightDp = 320,
    name = "Dark")
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun HexDisplayPreview(){
    IChingTheme {
        ExploreOracleGrid()
    }
}
