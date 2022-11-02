package com.erickuha.iching.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.erickuha.iching.R

@Composable
fun OraclePanel(
    modifier: Modifier = Modifier,
    isReadingComplete: Boolean,
) {
    Surface() {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(!isReadingComplete) {
                Text(text = stringResource(R.string.select_a_stalk))
            } else {
                Text(text = stringResource(R.string.reading_complete))
                Button(
                    onClick = {

                    }
                ){
                    Text(text = stringResource(id = R.string.continue_text))
                }
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OraclePanelPreview(){
    IChingTheme {
        OraclePanel(
            isReadingComplete = true
        )
    }
}

