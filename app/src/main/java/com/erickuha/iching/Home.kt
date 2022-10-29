package com.erickuha.iching

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.erickuha.iching.ui.theme.IChingTheme

@Composable
fun Home(modifier: Modifier = Modifier){
    var showOnboarding by rememberSaveable{ mutableStateOf(true) }
    Surface(modifier, color = MaterialTheme.colorScheme.background){
        if (showOnboarding){
            OnboardingScreen(onContinueClicked = { showOnboarding = false })
        } else {
            Oracle()
        }
    }
}

@Composable
fun Oracle(
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        YarrowStalks()
    }
//    Row(modifier = modifier.fillMaxWidth()){
//        OraclePanel()
//        HexDisplay()
//    }
}

//@Composable
//fun HexDisplay() {
//    TODO("Not yet implemented")
//}
//
//@Composable
//fun OraclePanel() {
//    TODO("Not yet implemented")
//}

@Composable
fun YarrowStalks(
    modifier: Modifier = Modifier,
){
    Row(
        modifier.padding(1.dp),
        horizontalArrangement = Arrangement.Center,
    ){
        Image(
            modifier = modifier.weight(1f),
            painter = painterResource(id = R.drawable.stalk_r),
            contentDescription = null,
        )
    }
    Row (
        modifier.padding(10.dp)
    ) {
        for (i in 1..49) {
            YarrowStalk(
                modifier = Modifier.weight(1f),
                index = i)
        }
    }
}

@Composable
fun YarrowStalk(
    modifier: Modifier = Modifier,
    index: Int
){
    val context = LocalContext.current
    Image(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = {
                    Toast
                        .makeText(context, "$index clicked", Toast.LENGTH_SHORT)
                        .show()
                }
            ),
        painter = painterResource(id = R.drawable.stalk_n),
        contentDescription = null,
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
    widthDp = 320,
    heightDp = 320,
    name = "Dark")
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OraclePreview(){
    IChingTheme {
        Oracle()
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
