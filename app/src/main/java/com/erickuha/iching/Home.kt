package com.erickuha.iching

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

val colors = listOf(
    Color(0xFFffd7d7.toInt()),
    Color(0xFFffe9d6.toInt()),
    Color(0xFFfffbd0.toInt()),
    Color(0xFFe3ffd9.toInt()),
    Color(0xFFd0fff8.toInt())
)


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun LayoutTest(modifier: Modifier = Modifier){
    Scaffold(
        topBar = {
            TopAppBar (
                title = { Text("Scaffold Test")},
                navigationIcon = {
                    IconButton(
                        onClick = {/* TODO */}
                    ){
                        Icon(Icons.Filled.Menu, contentDescription = "Navigation")
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /*TODO*/ }) {
                Text("Inc")
            }
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.consumedWindowInsets(innerPadding),
                contentPadding = innerPadding
            ) {
                items(count = 100){
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(colors[it % colors.size])
                    )
                }
            }
        }

    )
}


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
                    isReadingComplete = true
                    },
                    onResultRequested = {
                        state = RESOLUTION_STATE
                    }
                )
            }
            RESOLUTION_STATE -> {
                OracleResultActivity(
                    modifier,
                    hexagramOne,
                    hexagramTwo
                )
            }
            else -> {
                throw Error("Invalid State Error")
            }
        }
    }
}

@Composable
fun OracleActivity(
    modifier: Modifier = Modifier,
    isReadingComplete: Boolean,
    oracleViewModel: OracleViewModel = viewModel(),
    onReadingComplete: (Hexagram, Hexagram) -> Unit,
    onResultRequested: () -> Unit,
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
                    isReadingComplete = isReadingComplete,
                    onResultRequested = onResultRequested,
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
            isReadingComplete = false,
            onResultRequested = {}
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

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 320,
    heightDp = 320,
    name = "Dark")
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun LayoutTestPreview(){
    IChingTheme {
        LayoutTest()
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