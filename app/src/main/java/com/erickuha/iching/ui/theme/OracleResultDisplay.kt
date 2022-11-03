package com.erickuha.iching.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erickuha.iching.R
import com.erickuha.iching.oracle.Hexagram

private const val TAG = "Oracle Result"

@Composable
fun OracleResultActivity(
    modifier: Modifier = Modifier,
    firstHexagram: Hexagram,
    movingHexagram: Hexagram,
){
    var renderingMoving by rememberSaveable { mutableStateOf(false) }

    Column(Modifier.fillMaxSize()){
        Row(
            Modifier
                .fillMaxWidth()
        ){
            Box(
                Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            ){
                HexagramBox(
                    modifier = modifier,
                    message = stringResource(R.string.your_hex_string),
                    hexagram = firstHexagram,
                    onHexClicked = {
                        renderingMoving = false
                    }
                )

            }
            if(movingHexagram.hexagramNumber != 0) {
                Box(
                    Modifier.weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    HexagramBox(
                        modifier = modifier,
                        message = stringResource(R.string.moving_to_string),
                        hexagram = movingHexagram,
                        onHexClicked = {
                            renderingMoving = true
                        }
                    )
                }
            }
        }
        Box(){
            if(renderingMoving)
                HexagramText(hexagram = movingHexagram)
            else
                HexagramText(hexagram = firstHexagram)
        }
    }
}

@Composable
fun HexagramText(
    hexagram: Hexagram,
) {
    val hexTextArray = stringArrayResource(id = hexagram.resId)
    Column(){
        Row(){
            Text(
                modifier = Modifier.padding(horizontal = 2.dp),
                text = hexTextArray[0],
                style = typography.titleLarge
            )
            Text(
                modifier = Modifier.padding(horizontal = 2.dp),
                text = hexTextArray[1],
                style = typography.titleLarge
            )
            Text(
                modifier = Modifier.padding(horizontal = 2.dp),
                text = hexTextArray[2],
                style = typography.titleLarge
            )
    }
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ){
            Text(text = hexTextArray[3])
        }
    }
}

@Composable
fun HexagramBox(
    modifier: Modifier = Modifier,
    message: String,
    hexagram: Hexagram,
    onHexClicked: () -> Unit,
){
    val hexTextArray = stringArrayResource(id = hexagram.resId)
    Column(modifier.fillMaxWidth()) {
        Text(
            text = message,
            fontSize = 20.sp,
        )
        Text(
            modifier = Modifier.clickable{onHexClicked()},
            text = hexTextArray[2],
            fontSize = 80.sp,
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 320,
    heightDp = 320,
    name = "Dark"
)
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun HexagramDisplayPreview(){
    IChingTheme {
        OracleResultActivity(firstHexagram = Hexagram.ABUNDANCE, movingHexagram = Hexagram.GORGE)
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 320,
    heightDp = 320,
    name = "Dark"
)
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun HexagramTextPreview (){
    IChingTheme {
        HexagramText(hexagram = Hexagram.BITING_THROUGH)
    }
}