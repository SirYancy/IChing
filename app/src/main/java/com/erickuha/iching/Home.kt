package com.erickuha.iching

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.erickuha.iching.ui.theme.*

private const val TAG = "Home Activity"

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
    oracleViewModel: OracleViewModel = viewModel()
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = modifier.fillMaxWidth()) {
            YarrowStalks(
                modifier,
                onYarrowSelected = { index ->
                    oracleViewModel.divideStalks(index)
                },
                getYarrowPiles = {
                    oracleViewModel.getPiles()
                }
            )
        }
        Row(modifier = modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ){
                HexDisplay(
                    modifier,
                    oracleViewModel.getLines()
                )
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

/** ------------ PREVIEWS ------------------- */

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

class Ref(var value: Int)

@Composable
inline fun LogCompositions(tag: String, msg: String){
    if(BuildConfig.DEBUG){
        val ref = remember {Ref(0)}
        SideEffect {
            ref.value++
        }
        Log.d(tag, "Compositions: $msg ${ref.value}")
    }
}