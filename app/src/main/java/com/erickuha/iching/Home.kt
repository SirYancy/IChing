package com.erickuha.iching

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.erickuha.iching.oracle.Hexagram
import com.erickuha.iching.ui.theme.*

private const val TAG = "Home Activity"
private const val ONBOARDING_STATE = 100
private const val READING_STATE = 200
private const val RESOLUTION_STATE = 300

@Composable
fun Home(modifier: Modifier = Modifier){
    var state by rememberSaveable{ mutableStateOf(ONBOARDING_STATE) }
    var isReadingComplete by rememberSaveable { mutableStateOf(false) }
    var hexagramOne by rememberSaveable { mutableStateOf(Hexagram.UNDEFINED) }
    var hexagramTwo by rememberSaveable { mutableStateOf(Hexagram.UNDEFINED) }
    Surface{
        when (state) {
            ONBOARDING_STATE -> {
                OnboardingScreen(onContinueClicked = { state = READING_STATE })
            }
            READING_STATE -> {
                OracleActivity(
                    isReadingComplete = isReadingComplete,
                    onReadingComplete = {
                        firstHex, secondHex ->
                    hexagramOne = firstHex
                    hexagramTwo = secondHex
                })
            }
            RESOLUTION_STATE -> {
                OracleResultActivity()
            }
            else -> {
                throw Error("Invalid State Error")
            }
        }
    }
}

@Composable
fun OracleResultActivity(
    modifier: Modifier = Modifier,

){

}

@Composable
fun OracleActivity(
    modifier: Modifier = Modifier,
    isReadingComplete: Boolean,
    oracleViewModel: OracleViewModel = viewModel(),
    onReadingComplete: (Hexagram, Hexagram) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = modifier.fillMaxWidth()) {
            YarrowStalks(
                modifier,
                onYarrowSelected = { index ->
                    oracleViewModel.divideStalks(index)
                },
                getYarrowPiles = {
                    oracleViewModel.getPiles()
                },
                isReadingComplete = isReadingComplete,
            )
        }
        Row(modifier = modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ){
                HexDisplay(
                    modifier,
                    oracleViewModel.getLines(),
                    onReadingComplete,
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
            ){
                OraclePanel(
                    isReadingComplete = isReadingComplete
                )
            }
        }
    }
}

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onContinueClicked: () -> Unit
){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = stringResource(R.string.i_ching_oracle))
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ){
            Text(stringResource(R.string.continue_text))
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
        OracleActivity(
            onReadingComplete = {
                _, _ -> },
            isReadingComplete = false
        )
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

/** -------------- HELPERS ------------------------*/

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

@Composable
fun getStringResourceByName(aString: String): Int {
    val context = LocalContext.current
    return context.resources.getIdentifier(aString, "string-array", context.packageName)
}