package com.erickuha.iching

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.erickuha.iching.oracle.Line
import com.erickuha.iching.oracle.Oracle
import com.erickuha.iching.ui.theme.IChingTheme

@Composable
fun Home(modifier: Modifier = Modifier){
    var showOnboarding by rememberSaveable{ mutableStateOf(true) }
    Surface(modifier, color = MaterialTheme.colorScheme.background){
        if (showOnboarding){
            OnboardingScreen(onContinueClicked = { showOnboarding = false })
        } else {
            OracleActivity()
        }
    }
}

@Composable
fun OracleActivity(
    modifier: Modifier = Modifier,
) {
    val oracle = Oracle()
    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = modifier.fillMaxWidth()) {
            YarrowStalks(modifier, oracle)
        }
        Row(modifier = modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ){
                HexDisplay(modifier, oracle)
            }
            Box(
                modifier = Modifier
                    .weight(1f)
            ){
                OraclePanel()
            }
        }
    }
}

@Composable
fun HexDisplay(
    modifier: Modifier = Modifier,
    oracle: Oracle
) {
    Surface() {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (i in (0..oracle.lines.lastIndex).reversed()) {
                val lineRes: Int
                when (oracle.lines[i]) {
                    Line.OLD_YIN -> lineRes = R.drawable.old_yin
                    Line.OLD_YANG -> lineRes = R.drawable.old_yang
                    Line.YOUNG_YANG -> lineRes = R.drawable.young_yang
                    Line.YOUNG_YIN -> lineRes = R.drawable.young_yin
                    Line.UNDEFINED -> lineRes = R.drawable.no_line
                }

                Image(
                    painter = painterResource(id = lineRes),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun OraclePanel(
    modifier: Modifier = Modifier,
) {
    Surface() {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Select a stalk")
        }
    }
}

@Composable
fun YarrowStalks(
    modifier: Modifier = Modifier,
    oracle: Oracle,
){
    var piles = remember { mutableListOf<Int>() }
    if (piles.isEmpty()){
        piles.add(49)
    }
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
                                oracle,
                                makeClickable,
                                onDivision = {
                                    val oldPile = piles[0] - it
                                    piles.add(it)
                                    piles[0] = oldPile
                                }
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
    oracle: Oracle,
    makeClickable: Boolean,
    onDivision: (pile: Int) -> Unit
){
    val context = LocalContext.current
    Image(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = makeClickable,
                onClick = {
                    val remainder = oracle.divideStalks(index)
                    onDivision(remainder)
                }
            ),
        painter = painterResource(id = R.drawable.stalk_n),
        contentDescription = null,
        contentScale = ContentScale.FillHeight
    )
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "The I Ching Oracle")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ){
            Text("Continue")
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 500,
    heightDp = 1000,
    name = "Dark")
@Preview(
    showBackground = true,
    widthDp = 500,
    heightDp = 1000,
    name = "Light")
@Composable
fun OraclePreview(){
    IChingTheme {
        OracleActivity()
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
fun OnBoardingPreview(){
    IChingTheme {
        Home()
    }
}